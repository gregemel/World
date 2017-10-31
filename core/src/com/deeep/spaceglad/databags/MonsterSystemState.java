package com.deeep.spaceglad.databags;


import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.deeep.spaceglad.services.ModelService;

import java.util.Random;

public class MonsterSystemState {
    private ImmutableArray<Entity> entities;
    private Entity player;
    private Quaternion quat = new Quaternion();
    private Engine engine;
    private GameWorld gameWorld;
    private Vector3 playerPosition = new Vector3();
    private Vector3 enemyPosition = new Vector3();
    private Matrix4 ghost = new Matrix4();
    private Vector3 translation = new Vector3();
    private Random random = new Random();
    private ModelService modelService = new ModelService();

    private float[] xSpawns = {12, -12, 112, -112};
    private float[] zSpawns = {-112, 112, -12, 12};

    private ComponentMapper<CharacterComponent> cm = ComponentMapper.getFor(CharacterComponent.class);
    private ComponentMapper<StatusComponent> sm = ComponentMapper.getFor(StatusComponent.class);

    public ImmutableArray<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ImmutableArray<Entity> entities) {
        this.entities = entities;
    }

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public Quaternion getQuat() {
        return quat;
    }

    public void setQuat(Quaternion quat) {
        this.quat = quat;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public Vector3 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Vector3 playerPosition) {
        this.playerPosition = playerPosition;
    }

    public Vector3 getEnemyPosition() {
        return enemyPosition;
    }

    public void setEnemyPosition(Vector3 enemyPosition) {
        this.enemyPosition = enemyPosition;
    }

    public Matrix4 getGhost() {
        return ghost;
    }

    public void setGhost(Matrix4 ghost) {
        this.ghost = ghost;
    }

    public Vector3 getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3 translation) {
        this.translation = translation;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public float[] getxSpawns() {
        return xSpawns;
    }

    public void setxSpawns(float[] xSpawns) {
        this.xSpawns = xSpawns;
    }

    public float[] getzSpawns() {
        return zSpawns;
    }

    public void setzSpawns(float[] zSpawns) {
        this.zSpawns = zSpawns;
    }

    public ComponentMapper<CharacterComponent> getCm() {
        return cm;
    }

    public void setCm(ComponentMapper<CharacterComponent> cm) {
        this.cm = cm;
    }

    public ComponentMapper<StatusComponent> getSm() {
        return sm;
    }

    public void setSm(ComponentMapper<StatusComponent> sm) {
        this.sm = sm;
    }
}