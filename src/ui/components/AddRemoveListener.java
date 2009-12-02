package ui.components;

public interface AddRemoveListener {
    public void objectAdded(Object o);
    public void objectRemoved(Object o, boolean wasSelected);

    public void objectSelected(Object o);
}
