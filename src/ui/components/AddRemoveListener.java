package ui.components;

public interface AddRemoveListener {
    public void objectAdded(Object o);
    public void objectRemoved(Object o);

    public void objectSelected(Object o);
    public void selectedObjectRemoved(Object o);
}
