package il.ac.hit;

import il.ac.hit.model.IModel;
import il.ac.hit.model.Model;
import il.ac.hit.view.IView;
import il.ac.hit.view.View;
import il.ac.hit.viewmodel.IViewModel;
import il.ac.hit.viewmodel.ViewModel;

import javax.swing.*;

public class Program {
    public static void main(String[] args) {
        // Creating instances of classes
        IModel model = new Model();
        IViewModel vm = new ViewModel();
        IView view = new View();

        // Sending the implemented classes as parameters
        vm.setModel(model);
        vm.setView(view);
        view.setIViewModel(vm);

        // Initializing the program and showing its window
        SwingUtilities.invokeLater(() -> {
            view.init();
            view.start();
        });

    }
}