package com.emelwerx.world.databags.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class ModelComponent extends Component {
    private Model model;
    private ModelInstance instance;
    private Vector3 position;
    private Matrix4 matrix4;
    private BlendingAttribute blendingAttribute;

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ModelInstance getInstance() {
        return instance;
    }

    public void setInstance(ModelInstance instance) {
        this.instance = instance;
    }

    public Matrix4 getMatrix4() {
        return matrix4;
    }

    public void setMatrix4(Matrix4 matrix4) {
        this.matrix4 = matrix4;
    }

    public BlendingAttribute getBlendingAttribute() {
        return blendingAttribute;
    }

    public void setBlendingAttribute(BlendingAttribute blendingAttribute) {
        this.blendingAttribute = blendingAttribute;
    }
}