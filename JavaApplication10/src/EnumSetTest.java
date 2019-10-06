import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public final class EnumSetTest {
    
    
    public static JFrame f = new JFrame();
    
    
  private JComponent makeUI() {
    String[] columnNames = {"Imię","Nazwisko","Adres","Pesel", "Opcje"};
    Object[][] data = {
      {"Jan","Nowakowski","Długa 2 Koszalin","1232342323", EnumSet.of(Actions.EDYTUJ,Actions.WIZYTY)},
      {"Piotr","Kowalski","Krótka 20 Koszalin","2332342323", EnumSet.of(Actions.EDYTUJ,Actions.WIZYTY)},
      {"Paweł","Nowak","Spokojna 23 Koszalin","5232342323", EnumSet.allOf(Actions.class)},
      {"Tadeusz","Spiewak","Akacjowa 21 Koszalin","6232342323", EnumSet.of(Actions.EDYTUJ,Actions.WIZYTY)}
    };
    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
      @Override public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }
    };
    
   
    
    JTable table = new JTable(model);
    
    table.setRowHeight(40);
    TableColumn column = table.getColumnModel().getColumn(4);
    column.setCellRenderer(new ButtonsRenderer());
    column.setCellEditor(new ButtonsEditor(table, f));
    return new JScrollPane(table);
  }
   
    
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override public void run() {
        
        FilterAdd pan = new FilterAdd();
        createAndShowGUI(f, pan);
      }
    });
  }
  public static void createAndShowGUI(JFrame f, JPanel pn) {
    
    f.setLayout(new GridLayout(2, 1));
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    f.getContentPane().add(new EnumSetTest().makeUI());
    f.add(pn);
    f.setSize(1024,415);
    f.setLocationRelativeTo(null);
    f.setVisible(true);
    f.setResizable(false);
  }
}

enum Actions { WIZYTY, EDYTUJ; }

class ButtonsPanel extends JPanel {
  public final List<JButton> buttons = new ArrayList<>();
  public ButtonsPanel() {
    super(new FlowLayout(FlowLayout.LEFT));
    setOpaque(true);
    for (Actions a : Actions.values()) {
      JButton b = new JButton(a.toString());
      b.setFocusable(false);
      b.setRolloverEnabled(false);
      add(b);
      buttons.add(b);
    }
  }
  protected void updateButtons(Object value) {
    if (value instanceof EnumSet) {
      EnumSet ea = (EnumSet) value;
      removeAll();
      if (ea.contains(Actions.WIZYTY)) {
        add(buttons.get(0));
      }
      if (ea.contains(Actions.EDYTUJ)) {
        add(buttons.get(1));
      }
    }
  }
}

class ButtonsRenderer implements TableCellRenderer {
  private final ButtonsPanel panel = new ButtonsPanel();
  @Override public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
    panel.updateButtons(value);
    return panel;
  }
}

class PrintAction extends AbstractAction {
  private final JTable table;
  private final JFrame frame;
  private Wizyty wiz;
    
  public PrintAction(JTable table, JFrame f) {
    super(Actions.WIZYTY.toString());
    this.table = table;
    this.frame = f;
  }
  @Override public void actionPerformed(ActionEvent e) {
  
      int row = table.convertRowIndexToModel(table.getEditingRow());
      String imie = table.getModel().getValueAt(row, 0).toString();
      String nazwisko = table.getModel().getValueAt(row, 1).toString();
      String pesel = table.getModel().getValueAt(row, 3).toString();
  wiz = new Wizyty(pesel, imie, nazwisko);
  wiz.setVisible(true);
  wiz.setLocationRelativeTo(null);
  wiz.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}

class EditAction extends AbstractAction {
  private final JTable table;
  private final JFrame frame;
  public EditAction(JTable table, JFrame f) {
    super(Actions.EDYTUJ.toString());
    this.table = table;
    this.frame = f;
  }
  @Override public void actionPerformed(ActionEvent e) {
    int row = table.convertRowIndexToModel(table.getEditingRow());
    Object o = table.getModel().getValueAt(row, 0);
    
    String imie = table.getModel().getValueAt(row, 0).toString();
    String nazwisko = table.getModel().getValueAt(row, 1).toString();
    String adres = table.getModel().getValueAt(row, 2).toString();
    String pesel = table.getModel().getValueAt(row, 3).toString();
    
    Pacjenci pacj = new Pacjenci(imie, nazwisko, adres, pesel);
    pacj.setVisible(true);
    pacj.setLocationRelativeTo(null);
    pacj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}

class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {
  private final ButtonsPanel panel = new ButtonsPanel();
  private final JTable table;
  private final JFrame frame;
  private Object o;
  private class EditingStopHandler extends MouseAdapter implements ActionListener {
    @Override public void mousePressed(MouseEvent e) {
      Object o = e.getSource();
      if (o instanceof TableCellEditor) {
        actionPerformed(null);
      } else if (o instanceof JButton) {
        ButtonModel m = ((JButton) e.getComponent()).getModel();
        if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
          panel.setBackground(table.getBackground());
        }
      }
    }
    @Override public void actionPerformed(ActionEvent e) {
      EventQueue.invokeLater(new Runnable() {
        @Override public void run() {
          fireEditingStopped();
        }
      });
    }
  }
  public ButtonsEditor(JTable table, JFrame f) {
    super();
    this.table = table;
    this.frame = f;
    panel.buttons.get(0).setAction(new PrintAction(table, frame));
    panel.buttons.get(1).setAction(new EditAction(table, frame));

    EditingStopHandler handler = new EditingStopHandler();
    for (JButton b : panel.buttons) {
      b.addMouseListener(handler);
      b.addActionListener(handler);
    }
    panel.addMouseListener(handler);
  }
  @Override public Component getTableCellEditorComponent(
      JTable table, Object value, boolean isSelected, int row, int column) {
    panel.setBackground(table.getSelectionBackground());
    panel.updateButtons(value);
    o = value;
    return panel;
  }
  @Override public Object getCellEditorValue() {
    return o;
  }
}
