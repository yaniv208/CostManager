package il.ac.hit;

public interface IViewModel {
    void setView(IView view);
    void setModel(IModel model);
    void addItem(Item item);
    void getItems();
    void deleteItem();
}
