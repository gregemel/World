package com.emelwerx.world.services;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.emelwerx.world.databags.AnimationComponent;
import com.emelwerx.world.databags.CharacterComponent;
import com.emelwerx.world.databags.World;
import com.emelwerx.world.databags.ModelComponent;
import com.emelwerx.world.databags.MonsterAnimations;
import com.emelwerx.world.databags.MonsterComponent;
import com.emelwerx.world.databags.ParticleComponent;
import com.emelwerx.world.databags.ThoughtComponent;
import com.emelwerx.world.systems.PhysicsSystem;

import java.util.Locale;
import java.util.Random;

public class MonsterFactory {

    private static Random random = new Random();

    private static Model cachedMonsterModel;
    private static final float modelScalar = 0.0025f;

    private static float[] xSpawns = {12, -12, 80, -80};
    private static float[] zSpawns = {-80, 80, -12, 12};

    public static Entity create(String name, World gameWorld) {
        Gdx.app.log("MonsterFactory", String.format(Locale.US,
                "creating monster %s, %s", name, gameWorld.toString()));

        float x = xSpawns[random.nextInt(xSpawns.length)];
        float y = 33;
        float z = zSpawns[random.nextInt(zSpawns.length)];

        Entity entity = new Entity();
        attachComponents(name, gameWorld, x, y, z, entity);
        return entity;
    }

    private static void attachComponents(String name, World gameWorld, float x, float y, float z, Entity entity) {
        ModelComponent modelComponent = getModelComponent(name, x, y, z);
        entity.add(modelComponent);

        CharacterComponent characterComponent = CharacterComponentFactory.create(entity, modelComponent);
        entity.add(characterComponent);

        PhysicsSystem physicsSystem = gameWorld.getPhysicsSystem();
        setPhysics(physicsSystem, entity);

        MonsterComponent monsterComponent = getMonsterComponent();
        entity.add(monsterComponent);

        AnimationComponent animationComponent = getAnimationComponent(modelComponent);
        entity.add(animationComponent);

        ThoughtComponent thoughtComponent = getThoughtComponent(animationComponent);
        entity.add(thoughtComponent);

        ParticleComponent particleComponent = getParticleComponent(gameWorld);
        entity.add(particleComponent);
    }

    private static ModelComponent getModelComponent(String name, float x, float y, float z) {
        Model model = getCachedMonsterModel(name);
        ModelComponent monsterModelComponent = ModelComponentFactory.create(model, x, y, z);

        Material material = monsterModelComponent.getInstance().materials.get(0);
        BlendingAttribute blendingAttribute;
        material.set(blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        monsterModelComponent.setBlendingAttribute(blendingAttribute);

        Matrix4 matrix4 = monsterModelComponent.getMatrix4();
        monsterModelComponent.getInstance().transform.set(matrix4.setTranslation(x, y, z));
        return monsterModelComponent;
    }

    private static Model getCachedMonsterModel(String name) {
        if (cachedMonsterModel == null) {
            cachedMonsterModel = ModelLoader.loadModel(name, modelScalar);
        }
        return cachedMonsterModel;
    }

    private static MonsterComponent getMonsterComponent() {
        return new MonsterComponent(MonsterComponent.STATE.HUNTING);
    }

    private static ParticleComponent getParticleComponent(World gameWorld) {
        ParticleSystem particleSystem = gameWorld.getRenderSystem().getRenderSystemState().getParticleSystem();
        return ParticleFactory.create("dieparticle", particleSystem);
    }

    private static ThoughtComponent getThoughtComponent(AnimationComponent animationComponent) {
        return ThoughtComponentFactory.create(animationComponent);
    }

    private static AnimationComponent getAnimationComponent(ModelComponent modelComponent) {
        AnimationService animationService = new AnimationService();
        AnimationComponent animationComponent = animationService.create(modelComponent.getInstance());
        animationService.animate(
                animationComponent,
                MonsterAnimations.getId(),
                MonsterAnimations.getOffsetRun1(),
                MonsterAnimations.getDurationRun1(),
                -1, 1);
        return animationComponent;
    }

    private static void setPhysics(PhysicsSystem physicsSystem, Entity entity) {
        btDiscreteDynamicsWorld collisionWorld = physicsSystem.getPhysicsSystemState().getCollisionWorld();
        CharacterComponent characterComponent = entity.getComponent(CharacterComponent.class);
        collisionWorld.addCollisionObject(
                characterComponent.getGhostObject(),
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        collisionWorld.addAction(
                characterComponent.getCharacterController());
    }
}