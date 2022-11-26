import dto.manufacturerDTO;
import dto.BrandDTO;
import rmi.Backend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class App extends JFrame {
    private static JFrame frame;

    private static Backend backend = null;

    private static manufacturerDTO currentManufacturer = null;
    private static BrandDTO currentBrand = null;

    private static boolean editMode = false;
    private static boolean manufacturerMode = true;

    private static JButton btnAddManufacturer = new JButton("Add Manufacturer");
    private static JButton btnAddBrand = new JButton("Add Brand");
    private static JButton btnEdit = new JButton("Edit Data");
    private static JButton btnBack = new JButton("Back");
    private static JButton btnSave = new JButton("Save");
    private static JButton btnDelete = new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box bookPanel = Box.createVerticalBox();
    private static Box manufacturerPanel = Box.createVerticalBox();

    private static JComboBox comboManufacturer = new JComboBox();
    private static JComboBox comboBrand = new JComboBox();

    private static JTextField textManufacturerName = new JTextField(30);
    private static JTextField textBrandName = new JTextField(30);
    private static JTextField textBrandManufacturerName = new JTextField(30);
    private static JTextField textBrandYear = new JTextField(30);

    private App() {
        super("Car dealership");
        frame = this;
        frame.setPreferredSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();
        sizeAllElements();
        frame.setLayout(new FlowLayout());

        // Menu
        menuPanel.add(btnAddManufacturer);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddManufacturer.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                manufacturerMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                manufacturerPanel.setVisible(true);
                bookPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddBrand);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddBrand.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                manufacturerMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                manufacturerPanel.setVisible(false);
                bookPanel.setVisible(true);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        menuPanel.add(Box.createVerticalStrut(20));
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                manufacturerPanel.setVisible(false);
                bookPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Manufacturer:"));
        comboPanel.add(comboManufacturer);
        comboPanel.add(Box.createVerticalStrut(20));
        comboManufacturer.addActionListener(e -> {
            String name = (String) comboManufacturer.getSelectedItem();
            try {
                currentManufacturer = backend.manufacturerFindByName(name);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            manufacturerMode = true;
            manufacturerPanel.setVisible(true);
            bookPanel.setVisible(false);
            fillAuthorFields();
            pack();
        });
        comboPanel.add(new JLabel("Brand:"));
        comboPanel.add(comboBrand);
        comboPanel.add(Box.createVerticalStrut(20));
        comboBrand.addActionListener(e -> {
            String name = (String) comboBrand.getSelectedItem();
            try {
                currentBrand = backend.brandFindByName(name);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            manufacturerMode = false;
            manufacturerPanel.setVisible(false);
            bookPanel.setVisible(true);
            try {
                fillBookFields();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            pack();
        });
        try {
            fillComboBoxes();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        comboPanel.setVisible(false);

        bookPanel.add(new JLabel("Name:"));
        bookPanel.add(textBrandName);
        bookPanel.add(Box.createVerticalStrut(20));
        bookPanel.add(new JLabel("Manufacturer Name:"));
        bookPanel.add(textBrandManufacturerName);
        bookPanel.add(Box.createVerticalStrut(20));
        bookPanel.add(new JLabel("Release year:"));
        bookPanel.add(textBrandYear);
        bookPanel.add(Box.createVerticalStrut(20));
        bookPanel.setVisible(false);

        manufacturerPanel.add(new JLabel("Name:"));
        manufacturerPanel.add(textManufacturerName);
        manufacturerPanel.add(Box.createVerticalStrut(20));
        manufacturerPanel.setVisible(false);

        actionPanel.add(btnSave);
        actionPanel.add(Box.createVerticalStrut(20));
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createVerticalStrut(20));
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                try {
                    delete();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        actionPanel.add(btnBack);
        actionPanel.add(Box.createVerticalStrut(20));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                manufacturerPanel.setVisible(false);
                bookPanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(manufacturerPanel);
        box.add(bookPanel);
        box.add(actionPanel);
        setContentPane(box);
        pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddManufacturer.setMaximumSize(dimension);
        btnAddBrand.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddManufacturer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddBrand.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        bookPanel.setPreferredSize(panelDimension);
        manufacturerPanel.setPreferredSize(panelDimension);

        comboManufacturer.setPreferredSize(dimension);
        comboBrand.setPreferredSize(dimension);

        textBrandManufacturerName.setPreferredSize(dimension);
        textBrandName.setPreferredSize(dimension);
        textBrandYear.setPreferredSize(dimension);
        textManufacturerName.setPreferredSize(dimension);
    }

    private static void save() {
        if (editMode) {
            if (manufacturerMode) {
                currentManufacturer.setName(textManufacturerName.getText());
                try {
                    if (backend.manufacturerUpdate(currentManufacturer)) {
                        JOptionPane.showMessageDialog(null, "Error: update failed!");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                currentBrand.setName(textBrandName.getText());
                currentBrand.setReleaseYear(Integer.parseInt(textBrandYear.getText()));

                manufacturerDTO author = null;
                try {
                    author = backend.manufacturerFindByName(textBrandManufacturerName.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (author == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such manufacturer!");
                    return;
                }
                currentBrand.setManufacturerId(author.getId());

                try {
                    if (!backend.brandUpdate(currentBrand)) {
                        JOptionPane.showMessageDialog(null, "Error: update failed!");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (manufacturerMode) {
                manufacturerDTO manufacturer = new manufacturerDTO();
                manufacturer.setName(textManufacturerName.getText());
                try {
                    if (!backend.manufacturerInsert(manufacturer)) {
                        JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                        return;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                comboManufacturer.addItem(manufacturer.getName());
            } else {
                BrandDTO brand = new BrandDTO();
                brand.setName(textBrandName.getText());
                brand.setReleaseYear(Integer.parseInt(textBrandYear.getText()));

                manufacturerDTO manufacturer = null;
                try {
                    manufacturer = backend.manufacturerFindByName(textBrandManufacturerName.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (manufacturer == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such manufacturer!");
                    return;
                }
                brand.setManufacturerId(manufacturer.getId());

                try {
                    if (!backend.brandInsert(brand)) {
                        JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                        return;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                comboBrand.addItem(brand.getName());
            }
        }
    }

    private static void delete() throws RemoteException {
        if (editMode) {
            if (manufacturerMode) {
                List<BrandDTO> list = backend.brandFindByAuthorId(currentManufacturer.getId());
                assert list != null;
                for (BrandDTO m : list) {
                    comboBrand.removeItem(m.getName());
                    backend.brandDelete(m);
                }
                backend.manufacturerDelete(currentManufacturer);
                comboManufacturer.removeItem(currentManufacturer.getName());
            } else {
                backend.brandDelete(currentBrand);
                comboBrand.removeItem(currentBrand.getName());
            }
        }
    }

    private void fillComboBoxes() throws RemoteException {
        comboManufacturer.removeAllItems();
        comboBrand.removeAllItems();
        List<manufacturerDTO> manufacturers = backend.manufacturerFindAll();
        List<BrandDTO> brands = backend.brandFindAll();
        assert manufacturers != null;
        for (manufacturerDTO author : manufacturers) {
            comboManufacturer.addItem(author.getName());
        }
        assert brands != null;
        for (BrandDTO book : brands) {
            comboBrand.addItem(book.getName());
        }
    }

    private static void clearFields() {
        textManufacturerName.setText("");
        textBrandName.setText("");
        textBrandManufacturerName.setText("");
        textBrandYear.setText("");
        currentManufacturer = null;
        currentBrand = null;
    }

    private static void fillAuthorFields() {
        if (currentManufacturer == null)
            return;
        textManufacturerName.setText(currentManufacturer.getName());
    }

    private static void fillBookFields() throws RemoteException {
        if (currentBrand == null)
            return;
        manufacturerDTO manufacturer = backend.manufacturerFindById(currentBrand.getManufacturerId());
        textBrandName.setText(currentBrand.getName());
        assert manufacturer != null;
        textBrandManufacturerName.setText(manufacturer.getName());
        textBrandYear.setText(String.valueOf(currentBrand.getReleaseYear()));
    }

    public static void main(String[] args) throws IOException, NotBoundException {
        String url = "//localhost:1234";
        backend = (Backend) Naming.lookup(url);
        JFrame myWindow = new App();
        myWindow.setVisible(true);
    }
}
