package com.emelwerx.world.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.emelwerx.world.databags.StatusSystemState;
import com.emelwerx.world.databags.ThoughtComponent;

import static java.lang.String.format;


public class ThinkingSystem extends EntitySystem {

    private final float deathDuration = 3.4f;
    private StatusSystemState statusSystemState;

    public void setStatusSystemState(StatusSystemState statusSystemState) {
        this.statusSystemState = statusSystemState;
    }

    @Override
    public void addedToEngine(Engine engine) {
        Gdx.app.log("ThinkingSystem", "addedToEngine");
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(ThoughtComponent.class).get());
        statusSystemState.setEntities(entities);
    }

    @Override
    public void update(float delta) {
        for(Entity entity: statusSystemState.getEntities()) {
            ThoughtComponent thoughtComponent = entity.getComponent(ThoughtComponent.class);

            if (!thoughtComponent.isAlive()) {
                float timeSinceDeath = thoughtComponent.getTimeSinceDeath() + delta;
                if (timeSinceDeath >= deathDuration) {
                    Gdx.app.log("ThinkingSystem", format("times up for %s", entity.toString()));
                    statusSystemState.getWorldService().removeEntityFromWorld(statusSystemState.getGameWorld(), entity);
                } else {
                    thoughtComponent.setTimeSinceDeath(timeSinceDeath);
                }
            }

        }
    }
}
