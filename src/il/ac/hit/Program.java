package il.ac.hit;

import javax.swing.*;

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
    }
}