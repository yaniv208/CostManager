package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Program
{
    public static void main(String[] args)
    {
        IModel model = new Model();
        IViewModel vm = new ViewModel();
        IView view = new View();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                view.init();
                view.start();
            }
        });

        vm.setModel(model);
        vm.setView(view);
        view.setIViewModel(vm);

        LoginWindow lp = new LoginWindow();
        lp.start();
    }
}