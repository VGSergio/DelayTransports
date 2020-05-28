/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delaytransports;

import java.awt.event.ItemEvent;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @authors Sergio Vega     (43480752B)
 *          Andreas Korn    (X4890193W)
 */
public class Delays extends javax.swing.JFrame {

    //static variables to share on all the class
    private static DelayTransport[] DelayTransport;
    private static int[] Lines,Directions,Stops,Hours;
    private static String[] Dates;
    
    /**
     * Method that reads delays of a transports system reading the specified filename
     * and saves it into DelayTransport array
     * @param filename
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    public static void readDelays(String filename) throws FileNotFoundException, IOException, ParseException{
        try (FileReader fr = new FileReader(filename); BufferedReader br = new BufferedReader(fr)) {
            Random Rnd = new Random();  //for unknown line number and for Tram's power, Trolley's floors and Bus's emissions
            ArrayList<DelayTransport> DelayTransportList = new ArrayList<>(); //DelayTransport ArrayList to insert as many as needed
            DelayTransport Transport;
            
            br.readLine(); //The first line has no relevant information
            String Transport_Delay = br.readLine();
            
            while (Transport_Delay!=null){
                //We insert the different items in separate indexes of the array
                String[] TransportDelay = Transport_Delay.split(";");   //Splits the read line in each data we need
                //We get the data from the read line
                int Delay = Integer.valueOf(TransportDelay[0]);
                String vehicletype = TransportDelay[1];
                int linenumber;
                if ("?".equals(TransportDelay[2]))
                    linenumber = Rnd.nextInt(919)+1;
                else
                    linenumber = Integer.valueOf(TransportDelay[2]);
                int direction = Integer.valueOf(TransportDelay[3]);
                int stopid = Integer.valueOf(TransportDelay[4]);
                int weekday = Integer.valueOf(TransportDelay[5]);
                String[] aux = TransportDelay[6].split(" "); //Splits the date in date and hour
                String date = aux[0];
                String hour = aux[1];
                //Depending of the vehicle type we create different instances
                switch (vehicletype) {
                    case "Tram":
                        int Potencia = Rnd.nextInt(401);
                        Transport = new DelayTram(Delay, vehicletype, linenumber, direction, stopid, weekday, date, hour, Potencia);
                        DelayTransportList.add(Transport);
                        break;
                    case "Trolley":
                        int Pisos = Rnd.nextInt(2)+1;
                        Transport = new DelayTrolley(Delay, vehicletype, linenumber, direction, stopid, weekday, date, hour, Pisos);
                        DelayTransportList.add(Transport);
                        break;
                    case "Bus":
                        int Emisiones = Rnd.nextInt(201);
                        Transport = new DelayBus(Delay, vehicletype, linenumber, direction, stopid, weekday, date, hour, Emisiones);
                        DelayTransportList.add(Transport);
                        break;
                }
                
                Transport_Delay = br.readLine();    //reads next delay
            }
            //Sets DelayTransport values to the read ones
            DelayTransport = new DelayTransport[DelayTransportList.size()];
            DelayTransportList.toArray(DelayTransport);
            //Closes the file
        }
    }
    
    /**
     * Method that gets how many different LineNumbers, Directions, StopIDs, Dates and Hours have been read.
     */
    public static void getData(){
        ArrayList<Integer> ln = new ArrayList<>();      //ArrayList to add as many LineNumbers as needed
        ArrayList<Integer> drs = new ArrayList<>();     //ArrayList to add as many Directions as needed
        ArrayList<Integer> stps = new ArrayList<>();    //ArrayList to add as many StopIDs as needed
        ArrayList<String> dat = new ArrayList<>();    //ArrayList to add as many StopIDs as needed
        ArrayList<String> hrs = new ArrayList<>();    //ArrayList to add as many StopIDs as needed
        //Firsts objects of the lists
        ln.add(DelayTransport[0].getLineNumber());
        drs.add(DelayTransport[0].getDirection());
        stps.add(DelayTransport[0].getStopID());
        dat.add(DelayTransport[0].getDate());
        String[] auxs = DelayTransport[0].getHour().split(":");
        hrs.add(auxs[0]);
        
        for (delaytransports.DelayTransport DelayTransport1 : DelayTransport) {
            //Next objects to analyze
            int l = DelayTransport1.getLineNumber();
            int d = DelayTransport1.getDirection();
            int s = DelayTransport1.getStopID();
            String dt = DelayTransport1.getDate();
            auxs = DelayTransport1.getHour().split(":");
            String h = auxs[0];
            //booleans to know if a object is already in the list or not
            boolean lIsInArray = false;
            boolean dIsInArray = false;
            boolean sIsInArray = false;
            boolean dtIsInArray = false;
            boolean hIsInArray = false;
            //Checks if the read items are in their respective list, if not, it's added
            //Lines
            for(int j=0;j<ln.size() && !lIsInArray ;j++){
                if (ln.get(j)==l){
                    lIsInArray = true;
                }
            }
            if(!lIsInArray){
                ln.add(l);
            }
            //Directions
            for(int j=0;j<drs.size() && !dIsInArray ;j++){
                if (drs.get(j)==d){
                    dIsInArray = true;
                }
            }
            if(!dIsInArray){
                drs.add(d);
            }
            //Stops
            for(int j=0;j<stps.size() && !sIsInArray;j++){
                if (stps.get(j)==s){
                    sIsInArray = true;
                }
            }
            if(!sIsInArray){
                stps.add(s);
            }
            //Dates
            for(int j=0;j<dat.size() && !dtIsInArray;j++){
                if (dt.equals(dat.get(j))){
                    dtIsInArray = true;
                }
            }
            if(!dtIsInArray){
                dat.add(dt);
            }
            //Hours
            for(int j=0;j<hrs.size() && !hIsInArray;j++){
                if (h.equals(hrs.get(j))){
                    hIsInArray = true;
                }
            }
            if(!hIsInArray){
                hrs.add(h);
            }
        }
        Lines = new int[ln.size()];
        Directions = new int[drs.size()];
        Stops = new int[stps.size()];
        Dates = new String[dat.size()];
        Hours = new int[hrs.size()];
        //ArrayList to array conversion
        //Lines
        for (int i=0; i<Lines.length; i++){
            Lines[i] = ln.get(i);
        }
        //Directions
        for (int i=0; i<Directions.length; i++){
            Directions[i] = drs.get(i);
        }
        //Stops
        for (int i=0; i<Stops.length; i++){
            Stops[i] = stps.get(i);
        }
        //Dates
        for (int i=0; i<Dates.length; i++){
            Dates[i] = dat.get(i);
        }
        //Hours
        for (int i=0; i<Hours.length; i++){
            Hours[i] = Integer.valueOf(hrs.get(i));
        }
        Arrays.sort(Lines);
        Arrays.sort(Directions);
        Arrays.sort(Stops);
        Arrays.sort(Dates);
        Arrays.sort(Hours);
    }
    
    
    /**
     * Creates new form Delays
     */
    public Delays() {
        initComponents();
        setLocationRelativeTo(null);    //frame centered on the window
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FileChooser = new javax.swing.JFileChooser();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        MaxDirectionPicker = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        MaxHourPicker = new javax.swing.JComboBox();
        MaxWeekDayPicker = new javax.swing.JComboBox();
        AvgDelayDisplay = new javax.swing.JTextField();
        GetDelayButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        MinDirectionPicker = new javax.swing.JComboBox();
        MaxLineNumberPicker = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        FileDisplay = new javax.swing.JTextField();
        MinDelayDisplay = new javax.swing.JTextField();
        MinHourPicker = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        MinDatePicker = new javax.swing.JComboBox();
        MaxDatePicker = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        MaxStopIDPicker = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        FilePicker = new javax.swing.JButton();
        MaxDelayDisplay = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        VehicleSelect = new javax.swing.JList<>();
        MinStopIDPicker = new javax.swing.JComboBox();
        MinLineNumberPicker = new javax.swing.JComboBox();
        MinWeekDayPicker = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();

        FileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\sergi\\OneDrive - Universitat de les Illes Balears\\UIB\\Segundo\\Primer Cuatrimestre\\Algoritmia\\DelayTransports"));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Delay Transport");
        setLocation(new java.awt.Point(0, 0));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        jLabel6.setText("Direction");

        jLabel9.setText("Date");

        jLabel1.setText("Choosed file:");

        MaxWeekDayPicker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));
        MaxWeekDayPicker.setSelectedIndex(-1);

        AvgDelayDisplay.setEditable(false);

        GetDelayButton.setText("Get delay");
        GetDelayButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GetDelayButtonMouseReleased(evt);
            }
        });

        jLabel10.setText("Hour");

        MinDirectionPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MinDirectionPickerItemStateChanged(evt);
            }
        });

        jLabel11.setText("Minimum delay");

        jLabel2.setText("Vehicle Type:");

        jLabel8.setText("WeekDay");

        FileDisplay.setEditable(false);

        MinDelayDisplay.setEditable(false);

        MinHourPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MinHourPickerItemStateChanged(evt);
            }
        });

        jLabel5.setText("To:");

        jLabel3.setText("Line Number:");

        MinDatePicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MinDatePickerItemStateChanged(evt);
            }
        });

        jLabel4.setText("From:");

        jLabel12.setText("Average delay");

        jLabel7.setText("StopID");

        FilePicker.setText("Choose file");
        FilePicker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                FilePickerMouseReleased(evt);
            }
        });

        MaxDelayDisplay.setEditable(false);

        jLabel13.setText("Maximum delay");

        VehicleSelect.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Tram", "Trolley", "Bus" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(VehicleSelect);

        MinStopIDPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MinStopIDPickerItemStateChanged(evt);
            }
        });

        MinLineNumberPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MinLineNumberPickerItemStateChanged(evt);
            }
        });

        MinWeekDayPicker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));
        MinWeekDayPicker.setSelectedIndex(-1);
        MinWeekDayPicker.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MinWeekDayPickerItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(FilePicker)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addComponent(MinLineNumberPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(MinDirectionPicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(MinStopIDPicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(36, 36, 36)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(MaxLineNumberPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(MinWeekDayPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(MaxWeekDayPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(MinDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(MaxDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(MinHourPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(36, 36, 36)
                                        .addComponent(MaxHourPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(136, 136, 136)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(MaxDirectionPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(MaxStopIDPicker, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(GetDelayButton, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(MaxDelayDisplay)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(AvgDelayDisplay, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MinDelayDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(FilePicker)
                        .addComponent(FileDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(MinLineNumberPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxLineNumberPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(MinDirectionPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxDirectionPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MinDelayDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(MinStopIDPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxStopIDPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(MinWeekDayPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxWeekDayPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxDelayDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(MinDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(MinHourPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MaxHourPicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AvgDelayDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(GetDelayButton)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Data", jPanel1);

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Delay", "Vehicle type", "Line number", "Direction", "Stop_ID", "Weekday", "Date", "Hour"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setResizable(false);
            Table.getColumnModel().getColumn(1).setResizable(false);
            Table.getColumnModel().getColumn(2).setResizable(false);
            Table.getColumnModel().getColumn(3).setResizable(false);
            Table.getColumnModel().getColumn(4).setResizable(false);
            Table.getColumnModel().getColumn(5).setResizable(false);
            Table.getColumnModel().getColumn(6).setResizable(false);
            Table.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Table", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FilePickerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FilePickerMouseReleased
        //resets all the pickers
        MinLineNumberPicker.removeAllItems();   MaxLineNumberPicker.removeAllItems();
        MinDirectionPicker.removeAllItems();    MaxDirectionPicker.removeAllItems();
        MinStopIDPicker.removeAllItems();   MaxStopIDPicker.removeAllItems();
        MinDatePicker.removeAllItems();     MaxDatePicker.removeAllItems();
        MinHourPicker.removeAllItems();     MaxHourPicker.removeAllItems();
        int seleccion = FileChooser.showOpenDialog(FilePicker);
        if (seleccion == JFileChooser.APPROVE_OPTION){
            //We get the file name
            String filename = FileChooser.getSelectedFile().getName();
            FileDisplay.setText(filename);  //We show the file to read
            
            try {
                //Reads the file
                readDelays(filename);
            } catch (IOException | ParseException ex) {
                System.out.println("error at reading the file");
            }
            
            getData();
            //adds the data we got to their respective pickers
            //Line number
            for (int i=0; i<Lines.length; i++){
                MinLineNumberPicker.addItem(Lines[i]);
                MaxLineNumberPicker.addItem(Lines[i]);
            }
            MaxLineNumberPicker.removeItemAt(0);        //The first value appears 2 times in the max combobox
            MinLineNumberPicker.setSelectedIndex(0);
            MaxLineNumberPicker.setSelectedIndex(0);
            //Direction
            for (int i=0; i<Directions.length; i++){
                MinDirectionPicker.addItem(Directions[i]);
                MaxDirectionPicker.addItem(Directions[i]);
            }
            MaxDirectionPicker.removeItemAt(0);        //The first value appears 2 times in the max combobox
            MinDirectionPicker.setSelectedIndex(0);
            MaxDirectionPicker.setSelectedIndex(0);
            //Stop_ID
            for (int i=0; i<Stops.length; i++){
                MinStopIDPicker.addItem(Stops[i]);
                MaxStopIDPicker.addItem(Stops[i]);
            }
            MaxStopIDPicker.removeItemAt(0);        //The first value appears 2 times in the max combobox
            MinStopIDPicker.setSelectedIndex(0);
            MaxStopIDPicker.setSelectedIndex(0);
            //----------
            MinWeekDayPicker.setSelectedIndex(0);
            MaxWeekDayPicker.setSelectedIndex(0);
            //----------
            //Dates
            for (String Date : Dates) {
                MinDatePicker.addItem(Date);
                MaxDatePicker.addItem(Date);
            }
            MaxDatePicker.removeItemAt(0);        //The first value appears 2 times in the max combobox
            MinDatePicker.setSelectedIndex(0);
            MaxDatePicker.setSelectedIndex(0);
            //Hours
            for (int i=0; i<Hours.length; i++){
                MinHourPicker.addItem(Hours[i]+":00");
                MaxHourPicker.addItem(Hours[i]+":59");
            }
            MaxHourPicker.removeItemAt(0);        //The first value appears 2 times in the max combobox
            MinHourPicker.setSelectedIndex(0);
            MaxHourPicker.setSelectedIndex(0);
        }
    }//GEN-LAST:event_FilePickerMouseReleased

    private void MinLineNumberPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MinLineNumberPickerItemStateChanged
        //Everytime a min value is selected, max value combobox is updated
        if (evt.getStateChange() == ItemEvent.SELECTED){
            MaxLineNumberPicker.removeAllItems();
            for (int i = MinLineNumberPicker.getSelectedIndex(); i<MinLineNumberPicker.getItemCount();i++){
                MaxLineNumberPicker.addItem(MinLineNumberPicker.getItemAt(i));
            }
        }
    }//GEN-LAST:event_MinLineNumberPickerItemStateChanged

    private void MinDirectionPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MinDirectionPickerItemStateChanged
        //Everytime a min value is selected, max value combobox is updated
        if (evt.getStateChange() == ItemEvent.SELECTED){
            MaxDirectionPicker.removeAllItems();
            for (int i = MinDirectionPicker.getSelectedIndex(); i<MinDirectionPicker.getItemCount();i++){
                MaxDirectionPicker.addItem(MinDirectionPicker.getItemAt(i));
            }
        }
    }//GEN-LAST:event_MinDirectionPickerItemStateChanged

    private void MinStopIDPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MinStopIDPickerItemStateChanged
        //Everytime a min value is selected, max value combobox is updated
        if (evt.getStateChange() == ItemEvent.SELECTED){
            MaxStopIDPicker.removeAllItems();
            for (int i = MinStopIDPicker.getSelectedIndex(); i<MinStopIDPicker.getItemCount();i++){
                MaxStopIDPicker.addItem(MinStopIDPicker.getItemAt(i));
            }
        }
    }//GEN-LAST:event_MinStopIDPickerItemStateChanged

    private void MinWeekDayPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MinWeekDayPickerItemStateChanged
        //Everytime a min value is selected, max value combobox is updated
        if (evt.getStateChange() == ItemEvent.SELECTED){
            MaxWeekDayPicker.removeAllItems();
            for (int i = MinWeekDayPicker.getSelectedIndex(); i<MinWeekDayPicker.getItemCount();i++){
                MaxWeekDayPicker.addItem(MinWeekDayPicker.getItemAt(i));
            }
        }
    }//GEN-LAST:event_MinWeekDayPickerItemStateChanged

    private void MinDatePickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MinDatePickerItemStateChanged
        //Everytime a min value is selected, max value combobox is updated
        if (evt.getStateChange() == ItemEvent.SELECTED){
            MaxDatePicker.removeAllItems();
            for (int i = MinDatePicker.getSelectedIndex(); i<MinDatePicker.getItemCount();i++){
                MaxDatePicker.addItem(MinDatePicker.getItemAt(i));
            }
        }
    }//GEN-LAST:event_MinDatePickerItemStateChanged

    private void MinHourPickerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MinHourPickerItemStateChanged
       //Everytime a min value is selected, max value combobox is updated 
        if (evt.getStateChange() == ItemEvent.SELECTED){
            MaxHourPicker.removeAllItems();
            for (int i = MinHourPicker.getSelectedIndex(); i<MinHourPicker.getItemCount();i++){
                MaxHourPicker.addItem(MinHourPicker.getItemAt(i).toString().replace("00","59"));
            }
        }
    }//GEN-LAST:event_MinHourPickerItemStateChanged

    private void GetDelayButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GetDelayButtonMouseReleased
        //Cleans the table
        DefaultTableModel model = (DefaultTableModel) Table.getModel();
        model.setRowCount(0);
        
        int min = 0, max = 0, suma = 0, cont = 0, avg = 0, i = 0;
        //We get the selected vehicle types
        int[] vi = VehicleSelect.getSelectedIndices();
        String[] vn = new String[vi.length];
        for (int j= 0; j<vi.length; j++){
            vn[j] = VehicleSelect.getModel().getElementAt(vi[j]);
        }
        //We get min and max parameters
        //Line number
        int minln = Integer.valueOf(MinLineNumberPicker.getSelectedItem().toString());
        int maxln = Integer.valueOf(MaxLineNumberPicker.getSelectedItem().toString());
        
        //Direction
        int mind = Integer.valueOf(MinDirectionPicker.getSelectedItem().toString());
        int maxd = Integer.valueOf(MaxDirectionPicker.getSelectedItem().toString());
        
        //Stop_ID
        int mins = Integer.valueOf(MinStopIDPicker.getSelectedItem().toString());
        int maxs = Integer.valueOf(MaxStopIDPicker.getSelectedItem().toString());
        
        //Weekday
        int minw = MinWeekDayPicker.getSelectedIndex() + 1;
        int maxw = MaxWeekDayPicker.getSelectedIndex() + 1;
        
        //Minimum date
        String[] d = MinDatePicker.getSelectedItem().toString().split("/");
        int minda = Integer.valueOf(d[2] + d[1] + d[0]); //If we concatenate the data this way (yymmdd) we can compare the values directly
        //Maximum date
        String[] d2 = MaxDatePicker.getSelectedItem().toString().split("/");
        int maxda = Integer.valueOf(d2[2] + d2[1] + d2[0]); //If we concatenate the data this way (yymmdd) we can compare the values directly
        
        //Minimum hour
        String[] h = MinHourPicker.getSelectedItem().toString().split(":");
        int minh = Integer.valueOf(h[0]+h[1]);
        //Maximum hour
        String[] h2 = MaxHourPicker.getSelectedItem().toString().split(":");
        int maxh = Integer.valueOf(h2[0]+h2[1]);

        //booleans to know if the read delay has the same parameters as selected
        boolean vehicle, line, direction, stop, weekday, date, hour;
        //finds the first object with the specified parameters
        while (i < DelayTransport.length && min == 0) {
            vehicle = false;     line = false;  direction = false;
            stop = false;   weekday = false;     date = false;
            hour = false;
            //checks vehicle type
            for (String v : vn) {
                switch (v) {
                    case "Tram":
                        if (DelayTransport[i] instanceof DelayTram) {
                            vehicle = true;
                        }
                        break;
                    case "Trolley":
                        if (DelayTransport[i] instanceof DelayTrolley) {
                            vehicle = true;
                        }
                        break;
                    case "Bus":
                        if (DelayTransport[i] instanceof DelayBus) {
                            vehicle = true;
                        }
                        break;
                }
            }
            //checks line number
            if ((DelayTransport[i].getLineNumber() >= minln) && (DelayTransport[i].getLineNumber() <= maxln)) {
                line = true;
            }
            //checks direction
            if ((DelayTransport[i].getDirection() >= mind) && (DelayTransport[i].getDirection() <= maxd)) {
                direction = true;
            }
            //checks Stop_ID
            if ((DelayTransport[i].getStopID() >= mins) && (DelayTransport[i].getStopID() <= maxs)) {
                stop = true;
            }
            //checks weekday
            if ((DelayTransport[i].getWeekDay() >= minw) && (DelayTransport[i].getWeekDay() <= maxw)) {
                weekday = true;
            }
            //checks date
            String[] d3 = DelayTransport[i].getDate().split("/");
            int datenum = Integer.valueOf(d3[2] + d3[1] + d3[0]);
            if ((datenum >= minda) && (datenum <= maxda)) {
                date = true;
            }
            //checks hour
            String[] h3 = DelayTransport[i].getHour().split(":");
            int hournum = Integer.valueOf(h3[0] + h3[1]);
            if ((hournum >= minh) && (hournum <= maxh)) {
                hour = true;
            }
            // if all parameters coincide, updates the data
            if (vehicle && line && direction && stop && stop && weekday
                    && date && hour) {
                min = DelayTransport[i].getDelay();
                max = DelayTransport[i].getDelay();
                suma += DelayTransport[i].getDelay();
                cont++;
                //Adds the object to the table
                model = (DefaultTableModel) Table.getModel();
                model.addRow(new Object[]{DelayTransport[i].getDelay(), 
                    DelayTransport[i].getVehicleType(), DelayTransport[i].getLineNumber(),
                    DelayTransport[i].getDirection(), DelayTransport[i].getStopID(),
                    DelayTransport[i].getWeekDay(), DelayTransport[i].getDate(),
                    DelayTransport[i].getHour()});
            }
            i++;
        }
        
        while (i<DelayTransport.length) {
            vehicle = false;     line = false;  direction = false;
            stop = false;   weekday = false;     date = false;
            hour = false;
            //checks vehicle type
            for (String v : vn) {
                switch (v) {
                    case "Tram":
                        if (DelayTransport[i] instanceof DelayTram) {
                            vehicle = true;
                        }
                        break;
                    case "Trolley":
                        if (DelayTransport[i] instanceof DelayTrolley) {
                            vehicle = true;
                        }
                        break;
                    case "Bus":
                        if (DelayTransport[i] instanceof DelayBus) {
                            vehicle = true;
                        }
                        break;
                }
            }
            //checks line number
            if ((DelayTransport[i].getLineNumber() >= minln) && (DelayTransport[i].getLineNumber() <= maxln)) {
                line = true;
            }
            //checks direction
            if ((DelayTransport[i].getDirection() >= mind) && (DelayTransport[i].getDirection() <= maxd)) {
                direction = true;
            }
            //checks stop_id
            if ((DelayTransport[i].getStopID() >= mins) && (DelayTransport[i].getStopID() <= maxs)) {
                stop = true;
            }
            //checks weekday
            if ((DelayTransport[i].getWeekDay() >= minw) && (DelayTransport[i].getWeekDay() <= maxw)) {
                weekday = true;
            }
            //checks date
            String[] d3 = DelayTransport[i].getDate().split("/");
            int datenum = Integer.valueOf(d3[2] + d3[1] + d3[0]);
            if ((datenum >= minda) && (datenum <= maxda)) {
                date = true;
            }
            //checks hour
            String[] h3 = DelayTransport[i].getHour().split(":");
            int hournum = Integer.valueOf(h3[0] + h3[1]);
            if ((hournum >= minh) && (hournum <= maxh)) {
                hour = true;
            }
            // if all parameters coincide, updates the data
            if (vehicle && line && direction && stop && stop && weekday
                    && date && hour) {
                if (DelayTransport[i].getDelay() < min) {
                    min = DelayTransport[i].getDelay();
                }
                if (DelayTransport[i].getDelay() > max) {
                    max = DelayTransport[i].getDelay();
                }
                suma += DelayTransport[i].getDelay();
                cont++;
                //Adds the object to the table
                model = (DefaultTableModel) Table.getModel();
                model.addRow(new Object[]{DelayTransport[i].getDelay(), 
                    DelayTransport[i].getVehicleType(), DelayTransport[i].getLineNumber(),
                    DelayTransport[i].getDirection(), DelayTransport[i].getStopID(),
                    DelayTransport[i].getWeekDay(), DelayTransport[i].getDate(),
                    DelayTransport[i].getHour()});
            }
            i++;
        }
        // if none objects coincide with the parameters, warns the user
        if (cont == 0) {
            MinDelayDisplay.setText("No delays found for the specified parameters");
            MaxDelayDisplay.setText("No delays found for the specified parameters");
            AvgDelayDisplay.setText("No delays found for the specified parameters");
        } else {
            MinDelayDisplay.setText(String.valueOf(min));
            MaxDelayDisplay.setText(String.valueOf(max));
            AvgDelayDisplay.setText(String.valueOf(suma / cont));
        }
    }//GEN-LAST:event_GetDelayButtonMouseReleased
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Delays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Delays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Delays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Delays.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Delays().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AvgDelayDisplay;
    private javax.swing.JFileChooser FileChooser;
    private javax.swing.JTextField FileDisplay;
    private javax.swing.JButton FilePicker;
    private javax.swing.JButton GetDelayButton;
    private javax.swing.JComboBox MaxDatePicker;
    private javax.swing.JTextField MaxDelayDisplay;
    private javax.swing.JComboBox MaxDirectionPicker;
    private javax.swing.JComboBox MaxHourPicker;
    private javax.swing.JComboBox MaxLineNumberPicker;
    private javax.swing.JComboBox MaxStopIDPicker;
    private javax.swing.JComboBox MaxWeekDayPicker;
    private javax.swing.JComboBox MinDatePicker;
    private javax.swing.JTextField MinDelayDisplay;
    private javax.swing.JComboBox MinDirectionPicker;
    private javax.swing.JComboBox MinHourPicker;
    private javax.swing.JComboBox MinLineNumberPicker;
    private javax.swing.JComboBox MinStopIDPicker;
    private javax.swing.JComboBox MinWeekDayPicker;
    private javax.swing.JTable Table;
    private javax.swing.JList<String> VehicleSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
