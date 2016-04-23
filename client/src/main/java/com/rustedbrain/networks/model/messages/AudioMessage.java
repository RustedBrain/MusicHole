package com.rustedbrain.networks.model.messages;

public class AudioMessage extends SystemMessage {

    private Object object;

    @Override
    public String toString() {
        return "AudioMessage{" +
                "object=" + object +
                '}';
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
