
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GUI
{
    private JFrame frame;
    private JPanel mainPanel;
    private CustomPanel chartPanel;
    private JScrollPane tablePane;
    private JScrollPane chartPane;
    private JTable table;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton computeBtn;
    private JLabel wtLabel;
    private JLabel wtResultLabel;
    private JLabel tatLabel;
    private JLabel tatResultLabel;
    private JComboBox option;
    private DefaultTableModel model;
    private JLabel lblNewLabel_1;
    
    public GUI()
    {
        model = new DefaultTableModel(new String[]{"Process", "AT", "BT", "Priority (N)", "WT", "TAT"}, 0); // Rows of the table
        
        table = new JTable(model);  // Table created with the above rows
        table.setBackground(new Color(250, 250, 210));
        table.setFont(new Font("Tahoma", Font.BOLD, 14));
        table.setFillsViewportHeight(true);
        tablePane = new JScrollPane(table);
        tablePane.setBounds(25, 25, 940, 250);   // input chart size definition
        
        addBtn = new JButton("Add Process");             // for the add button in the page
        addBtn.setBounds(651, 280, 127, 25);			// add button size
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {  // to add new process with empty data sets
                model.addRow(new String[]{"", "", "", "", "", ""});
            } 
        });
        
        removeBtn = new JButton("Remove Process");    //remove button things (tp remove processes)
        removeBtn.setBounds(803, 280, 162, 25);
        removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        removeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {  // to remove the last process
                int row = table.getSelectedRow();
                
                if (row > -1) {
                    model.removeRow(row);
                }
            }
        });
        
        chartPanel = new CustomPanel();			// Gantt Chart showing pannel
//        chartPanel.setPreferredSize(new Dimension(700, 10));
        chartPanel.setBackground(Color.WHITE);
        chartPane = new JScrollPane(chartPanel);
        chartPane.setBounds(25, 310, 940, 100);
        
        wtLabel = new JLabel("Average Waiting Time:");    // Avg. Waiting time things
        wtLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        wtLabel.setBounds(25, 425, 210, 25);
        tatLabel = new JLabel("Average Turn Around Time:");   // TT Time things
        tatLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        tatLabel.setBounds(25, 450, 210, 25);
        wtResultLabel = new JLabel();
        wtResultLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        wtResultLabel.setBounds(263, 425, 180, 25);
        tatResultLabel = new JLabel();
        tatResultLabel.setBackground(new Color(160, 82, 45));
        tatResultLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        tatResultLabel.setBounds(263, 450, 180, 25);
        
        option = new JComboBox(new String[]{"FCFS", "SJF(N)", "Priority(N)", "RR"});   // option box in the bottom right corner
        option.setFont(new Font("Tahoma", Font.BOLD, 15));
        option.setBounds(880, 420, 85, 20);
        
        computeBtn = new JButton("Simulate");    // Simulation button for execute
        computeBtn.setBounds(863, 449, 102, 25);
        computeBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        computeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) option.getSelectedItem();
                CPUScheduler scheduler;
                
                switch (selected) {   // to call the specific algo file to execute the operation
                    case "FCFS":
                        scheduler = new FirstComeFirstServe();
                        break;
                    case "SJF(N)":
                        scheduler = new ShortestJobFirst();
                        break;
                    
                    case "Priority(N)":
                        scheduler = new PriorityNonPreemptive();
                        break;
                    
                    case "RR":
                        String tq = JOptionPane.showInputDialog("Time Quantum");  // Round Robin Cycle period
                        if (tq == null) {
                            return;
                        }
                        scheduler = new RoundRobin();
                        scheduler.setTimeQuantum(Integer.parseInt(tq)); 
                        break;
                    default:
                        return;
                }
                
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    String process = (String) model.getValueAt(i, 0);
                    int at = Integer.parseInt((String) model.getValueAt(i, 1));
                    int bt = Integer.parseInt((String) model.getValueAt(i, 2));
                    int pl;
                    
                    if (selected.equals("PSN") || selected.equals("PSP"))
                    {
                        if (!model.getValueAt(i, 3).equals(""))
                        {
                            pl = Integer.parseInt((String) model.getValueAt(i, 3));
                        }
                        else
                        {
                            pl = 1;
                        }
                    }
                    else
                    {
                        pl = 1;
                    }
                                        
                    scheduler.add(new Row(process, at, bt, pl));
                }
                
                scheduler.process();
                
                for (int i = 0; i < model.getRowCount(); i++)   // storing waiting and TT time in the arraylist of the processes
                {
                    String process = (String) model.getValueAt(i, 0);
                    Row row = scheduler.getRow(process);
                    model.setValueAt(row.getWaitingTime(), i, 4);
                    model.setValueAt(row.getTurnaroundTime(), i, 5);
                }
                
                wtResultLabel.setText(Double.toString(scheduler.getAverageWaitingTime()));   // Avg wt
                tatResultLabel.setText(Double.toString(scheduler.getAverageTurnAroundTime())); // avg tt
                
                chartPanel.setTimeline(scheduler.getTimeline());
            }
        });
        
        mainPanel = new JPanel(null);   
        mainPanel.setBackground(new Color(244, 164, 96));
        mainPanel.setForeground(new Color(255, 255, 255));
        mainPanel.setPreferredSize(new Dimension(1000, 500)); // WINDOW SiZE
        mainPanel.add(tablePane);
        mainPanel.add(addBtn);
        mainPanel.add(removeBtn);
        mainPanel.add(chartPane);
        mainPanel.add(wtLabel);
        mainPanel.add(tatLabel);
        mainPanel.add(wtResultLabel);
        mainPanel.add(tatResultLabel);
        mainPanel.add(option);
        mainPanel.add(computeBtn);
        
        frame = new JFrame("Scheduling Simulator");  //Window things 
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().add(mainPanel);
        
        JLabel lblNewLabel = new JLabel("Gantt Chart");   
        lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblNewLabel.setBounds(25, 285, 102, 20);
        mainPanel.add(lblNewLabel);
        
        lblNewLabel_1 = new JLabel("Select Algorithm:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(735, 420, 135, 20);
        mainPanel.add(lblNewLabel_1);
        frame.pack();
    }
    
    public static void main(String[] args)
    {
        new GUI();
    }
    
    class CustomPanel extends JPanel
    {   
        private List<Event> timeline;
        
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            if (timeline != null)
            {
//                int width = 30;
                
                for (int i = 0; i < timeline.size(); i++)
                {
                    Event event = timeline.get(i);
                    int x = 30 * (i + 1);
                    int y = 20;
                    
                    g.drawRect(x, y, 30, 30);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    g.drawString(event.getProcessName(), x + 10, y + 20);
                    g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    g.drawString(Integer.toString(event.getStartTime()), x - 5, y + 45);
                    
                    if (i == timeline.size() - 1)
                    {
                        g.drawString(Integer.toString(event.getFinishTime()), x + 27, y + 45);
                    }
                    
//                    width += 30;
                }
                
//                this.setPreferredSize(new Dimension(width, 75));
            }
        }
        
        public void setTimeline(List<Event> timeline)
        {
            this.timeline = timeline;
            repaint();
        }
    }
}
