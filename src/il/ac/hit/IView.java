package il.ac.hit;

import javax.swing.table.DefaultTableModel;
import java.util.Collection;

public interface IView {
    public void setIViewModel(IViewModel vm);
    public void init();
    public void start();
    public void showItems(DefaultTableModel model);
    public void setUserID(int userID);
}
