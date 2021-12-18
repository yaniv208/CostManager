package il.ac.hit;

public interface IViewModel {
    public void setView(IView view);
    public void setModel(IModel model);
    public void addItem(Item item);
    public void getItems();
    public void deleteItem();
    public void registerUser(User user);
}
