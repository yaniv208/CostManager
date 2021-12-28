package il.ac.hit;

import javax.swing.table.DefaultTableModel;
import java.util.Collection;

public interface IView {
    void setIViewModel(IViewModel vm);
    void init();
    void start();
    // void finish();
    void showItems(DefaultTableModel data);
}
