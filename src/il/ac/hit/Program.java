package il.ac.hit;

import il.ac.hit.model.IModel;
import il.ac.hit.model.Model;
import il.ac.hit.view.IView;
import il.ac.hit.view.View;
import il.ac.hit.viewmodel.IViewModel;
import il.ac.hit.viewmodel.ViewModel;

import javax.swing.*;

public class Program
{
    public static void main(String[] args)
    {
        IModel model = new Model();
        IViewModel vm = new ViewModel();
        IView view = new View();

        vm.setModel(model);
        vm.setView(view);
        view.setIViewModel(vm);

        SwingUtilities.invokeLater(() -> {
            view.init();
            view.start();
        });

    }
}