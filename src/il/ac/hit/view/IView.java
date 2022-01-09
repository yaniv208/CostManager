package il.ac.hit.view;

import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.Item;
import il.ac.hit.viewmodel.EnumConsumerOfCategories;
import il.ac.hit.viewmodel.IViewModel;
import java.util.List;

public interface IView {
    void setIViewModel(IViewModel vm);
    void init();
    void start();
    void showItems(List<Item> data);
    void setID (int id);
    void openMainWindowOnlyAndCloseOtherWindows();
    void switchFromLoginWindowToMainWindow();
    void switchFromRegistrationWindowToMainWindow();
    void openLoginWindowOnlyAndCloseOtherWindows();
    void showCategories(List<String> categories, EnumCategoryType currentCategoriesType, EnumConsumerOfCategories caller);
    void saveCurrenciesRates(float[] currencies);
}
