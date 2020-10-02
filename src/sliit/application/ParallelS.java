/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sliit.application;

import java.awt.GraphicsEnvironment;
import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CHATA
 */
public class ParallelS extends javax.swing.JFrame {
    Connection con = JavaConnect.connectdb();
    PreparedStatement ps = null;
    ResultSet rs = null;
    /**
     * Creates new form Home
     */
    
    
    public void addDataToTable(){
        String day;
        String dur;
        String psid;
        JavaConnect.connectdb();
        day = jComboBoxDay.getSelectedItem().toString();
        dur = jTextFieldDuration.getText().toString();
        psid = jTextFieldpsid.getText().toString();
        
        //checck data availability
        if(checkDataAvailable(day,dur,psid)){
            System.out.println("Data not available");
            //add data to the database
            try{
                        String sql = "INSERT INTO SLIIT.PARALLELS (DAY, DUR,PSID) VALUES (?, ?, ?)";
//                ps.setString(PROPERTIES, sql);
                    ps = con.prepareStatement(sql);
                    ps.setString (1, day);
                    ps.setString(2, dur);
                    ps.setString(3, psid);
                    boolean result = ps.execute();
                    System.out.println(result);
                    
                    DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                    dtm.setRowCount(0);
                    showNotOverLapTable();
                    
                    //remove content from input fields
                    jTextFieldDuration.setText("");  
                    jTextFieldpsid.setText("");  
                    }catch(SQLException ex){
                        System.out.println(ex);
                    }
            //end of add data to the database
            
    }else{
            System.out.println("data  available");
        }
    }
    
    public boolean checkDataAvailable(String day,String dur,String psid){
        String sql = "SELECT * FROM SLIIT.PARALLELS WHERE day = '"+day+"' AND dur = '"+dur+"'   AND psid = '"+psid+"'";
        try{
            Statement statement = con.createStatement();
 
            ResultSet results = statement.executeQuery(sql);
            
            if(results.next()){
                JOptionPane.showMessageDialog(null, "This data already available in the database!", "", HEIGHT);
                return false;
            }else{
                return true;
            }
            
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex, "", HEIGHT);
                return false;
            }
        
    }
    
    
    public ParallelS() {
        initComponents();
        GraphicsEnvironment env =GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JavaConnect.connectdb();
        jComboBoxDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Y1.S1", "Y1.S2","Y2.S1","Y2.S2","Y3.S1","Y3.S2","Y4.S1","Y4.S2" }));
        //times array
        showNotOverLapTable();
        jTable1.setRowHeight(40);
       ArrayList<String> arr = new ArrayList<String>();

        int i, j;
        for(i=0; i<24; i++) {
          for(j=0; j<4; j++) {
//            arr.push(i.toString() + ":" + (j===0 ? "00" : 15*j) );
//              arr.add(i+ ":" + (j===0 ? "00" : 15*j));
                if(j==0){
                    arr.add(i+":"+"00");
                }else{
                    arr.add(i+":"+(15*j));
                }
          }
        }
        DefaultComboBoxModel dml= new DefaultComboBoxModel();
        for (i = 0; i < arr.size(); i++) {
          dml.addElement(arr.get(i));
        }

//        jComboBoxTo.setModel(dml);
        

        DefaultComboBoxModel dml2= new DefaultComboBoxModel();
        for (i = 0; i < arr.size(); i++) {
          dml2.addElement(arr.get(i));
        }

        
        
    }
    
    
    
    public class parallelSessionsModel{
        public String day;
        public String dur;
        public String psid;
        
        public parallelSessionsModel(String day, String dur,String psid)
        {
            this.day= day;
            this.dur = dur;
            this.psid = psid;
        }
    }
    
    DefaultTableModel model;
// added rows from arraylist to jtable
    public void addRowToJTable(ArrayList<ParallelS.parallelSessionsModel> list)
    {
        model = (DefaultTableModel) jTable1.getModel();
//        ArrayList<Student.User> list = ListUsers();
        Object rowData[] = new Object[3];
        for(int i = 0; i < list.size(); i++)
        {
            rowData[0] = list.get(i).day;
            rowData[1] = list.get(i).dur;
            rowData[2] = list.get(i).psid;
            model.addRow(rowData);
        }
                
    }
    
    

 public void showNotOverLapTable(){
        String students = "SELECT * FROM SLIIT.PARALLELS";
            try{
//                ps = con.createStatement();
//                //            ps.setString(1,gettext cne eka);
//
//                rs = ps.executeQuery();
                Statement statement = con.createStatement();
 
                ResultSet results = statement.executeQuery(students);
                
                    while(results.next()){
                        ArrayList<ParallelS.parallelSessionsModel> list = new ArrayList<ParallelS.parallelSessionsModel>();
                        ParallelS.parallelSessionsModel u1 = new ParallelS.parallelSessionsModel(results.getString("DAY"),results.getString("DUr"),results.getString("PSID"));
                        list.add(u1);
                        addRowToJTable(list);
                        
//                        String data = results.getString("AYS");
// 
//                        System.out.println("Fetching data by column name for row " + results.getRow() + " : " + data);
 
                    }
//                if(rs.next()){
//                    JOptionPane.showMessageDialog(null, "Data available", students, HEIGHT);
//                    System.out.println(rs.findColumn("AYS"));
//                }else{
//                    JOptionPane.showMessageDialog(null, "Data not available", students, HEIGHT);
//                }
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex, students, HEIGHT);
            }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jComboBox4 = new javax.swing.JComboBox<>();
        kButton4 = new keeptoo.KButton();
        jButton16 = new javax.swing.JButton();
        jBtnTags = new javax.swing.JButton();
        jBtnTags2 = new javax.swing.JButton();
        jBtnTags1 = new javax.swing.JButton();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        kButton2 = new keeptoo.KButton();
        jLabel12 = new javax.swing.JLabel();
        jComboBoxDay = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        kButtonDelete = new keeptoo.KButton();
        jTextFieldDuration = new javax.swing.JTextField();
        jTextFieldpsid = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setText("Add Not Available Times");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 788, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 0, 102));
        jLabel3.setText("Working Hours");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 534, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        kGradientPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DASHBOARD");
        kGradientPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 170, 50));
        kGradientPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jButton11.setBackground(new java.awt.Color(204, 51, 255));
        jButton11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("   Lecturers");
        kGradientPanel2.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 250, 60));

        jButton12.setBackground(new java.awt.Color(151, 51, 255));
        jButton12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("  Students");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 250, 60));

        jButton15.setBackground(new java.awt.Color(102, 0, 255));
        jButton15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Not available Times");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 250, 60));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        kGradientPanel4.setkEndColor(new java.awt.Color(204, 204, 204));
        kGradientPanel4.setkStartColor(new java.awt.Color(204, 204, 204));

        jLabel8.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 0, 102));
        jLabel8.setText("Location");

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 609, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setText("Building Name");

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Room Number");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setText("Room Type");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        kButton4.setText("UPdate");
        kButton4.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        kButton4.setkEndColor(new java.awt.Color(0, 204, 51));
        kButton4.setkStartColor(new java.awt.Color(0, 255, 153));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(118, 118, 118)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.Alignment.TRAILING, 0, 297, Short.MAX_VALUE))))
                .addGap(105, 105, 105))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addComponent(kButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(kGradientPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        kGradientPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 1110, 800));

        jButton16.setBackground(new java.awt.Color(51, 0, 255));
        jButton16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton16.setForeground(new java.awt.Color(255, 255, 255));
        jButton16.setText("Not Overlap");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 250, 60));

        jBtnTags.setBackground(new java.awt.Color(51, 0, 255));
        jBtnTags.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jBtnTags.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTags.setText("Tags");
        jBtnTags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTagsActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jBtnTags, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 250, 60));

        jBtnTags2.setBackground(new java.awt.Color(51, 0, 255));
        jBtnTags2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jBtnTags2.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTags2.setText("Consecutive Sessions");
        jBtnTags2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTags2ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jBtnTags2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 250, 60));

        jBtnTags1.setBackground(new java.awt.Color(51, 0, 255));
        jBtnTags1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jBtnTags1.setForeground(new java.awt.Color(255, 255, 255));
        jBtnTags1.setText("Parallel Sessions");
        jBtnTags1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTags1ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jBtnTags1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 250, 60));

        kGradientPanel7.setkEndColor(new java.awt.Color(204, 204, 204));
        kGradientPanel7.setkStartColor(new java.awt.Color(204, 204, 204));

        jLabel2.setFont(new java.awt.Font("Algerian", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 102));
        jLabel2.setText("parallel sessions");

        javax.swing.GroupLayout kGradientPanel7Layout = new javax.swing.GroupLayout(kGradientPanel7);
        kGradientPanel7.setLayout(kGradientPanel7Layout);
        kGradientPanel7Layout.setHorizontalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel7Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );
        kGradientPanel7Layout.setVerticalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(kGradientPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addContainerGap(76, Short.MAX_VALUE))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153)));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Day");

        kButton2.setText("ADD");
        kButton2.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        kButton2.setkEndColor(new java.awt.Color(51, 51, 255));
        kButton2.setkStartColor(new java.awt.Color(255, 0, 255));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Duration");

        jComboBoxDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDayActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 0, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("parallel sessions");

        kButtonDelete.setText("Delete");
        kButtonDelete.setFont(new java.awt.Font("Algerian", 1, 18)); // NOI18N
        kButtonDelete.setkEndColor(new java.awt.Color(204, 0, 51));
        kButtonDelete.setkStartColor(new java.awt.Color(255, 102, 0));
        kButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButtonDeleteActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("PSID");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(kButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldpsid, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(204, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDay, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldpsid, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(169, 169, 169)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(454, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DAY", "Duration", "parallel session ID"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 1702, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1203, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        this.dispose();
        Student s = new Student();
        s.setVisible(true);        
                    
                    
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jBtnTagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTagsActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Tags tags = new Tags();
        tags.setVisible(true);
    }//GEN-LAST:event_jBtnTagsActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
        //Add data to table
        //validate data

        addDataToTable();
    }//GEN-LAST:event_kButton2ActionPerformed

    private void jComboBoxDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDayActionPerformed

    private void kButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButtonDeleteActionPerformed

                System.out.println(jTable1.getSelectedRow());
                int i = jTable1.getSelectedRow();
                if(i >= 0){
                    //sql part here
                    String day = (String) model.getValueAt(i, 0);
                    String dur = (String) model.getValueAt(i, 1);
                    String psid = (String) model.getValueAt(i, 2);
                    
                    String sql = "DELETE FROM SLIIT.PARALLELS WHERE day = ?  AND dur = ? AND psid = ?";
                    System.out.println(sql);

                    try{
                        ps = con.prepareStatement(sql);
                        ps.setString (1, day);
                        ps.setString (2, dur);
                        ps.setString(3, psid);
                        boolean result = ps.execute();
                        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                        dtm.setRowCount(0);
                        showNotOverLapTable();
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(null, e, "Delete error", HEIGHT);
                    }
//
        }else{
            JOptionPane.showMessageDialog(null, "Select a row to delete", "ERROR", HEIGHT);
            System.out.println("Delete error");
        }
    }//GEN-LAST:event_kButtonDeleteActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        WorkingH nt= new WorkingH();
        nt.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jBtnTags2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTags2ActionPerformed
        // TODO add your handling code here:
        //this is consseccetive sessions button
        this.dispose();
        consecutiveSessions cs = new consecutiveSessions();
        cs.setVisible(true);
    }//GEN-LAST:event_jBtnTags2ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        NotOverLap nOl = new NotOverLap();
        nOl.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jBtnTags1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTags1ActionPerformed
        // TODO add your handling code here:

        this.dispose();
        ParallelS ps = new ParallelS();
        ps.setVisible(true);

    }//GEN-LAST:event_jBtnTags1ActionPerformed

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
            java.util.logging.Logger.getLogger(ParallelS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParallelS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParallelS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParallelS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParallelS().setVisible(true);
            }
        });
    }

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnTags;
    private javax.swing.JButton jBtnTags1;
    private javax.swing.JButton jBtnTags2;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBoxDay;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextFieldDuration;
    private javax.swing.JTextField jTextFieldpsid;
    private keeptoo.KButton kButton2;
    private keeptoo.KButton kButton4;
    private keeptoo.KButton kButtonDelete;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel4;
    private keeptoo.KGradientPanel kGradientPanel7;
    // End of variables declaration//GEN-END:variables
}
