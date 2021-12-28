package il.ac.hit.view;

import il.ac.hit.model.Item;
import il.ac.hit.viewmodel.IViewModel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IView {
    void setIViewModel(IViewModel vm);
    void init();
    void start();
    void showItems(List<Item> data);
    void setID (int id);
    void setIsConnected(boolean answer);
}
