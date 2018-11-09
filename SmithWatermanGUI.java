package com.summer.sw;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SmithWatermanGUI extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField s_adr;
    private JTextField t_adr;
    private JTextField insert;
    private JTextField replace;
    private JTextField delete;
    private JTextField match;
    static final int[][] BLOSUM62 = {
            { 4,-1,-2,-2,0,-1,-1,0,-2,-1,-1,-1,-1,-2,-1,1,0,-3,-2,0}, 
            { -1, 5,0,-2,-3,1,0,-2,0,-3,-2,2,-1,-3,-2,-2,-1,-3,-2,-3 }, 
            { -2, 0,  6 ,1,-3,0,0,0,1,-3,-3,0,-2,-3,1,-2,0,-4,-2,-3}, 
            { -2, -2, 1, 6  ,-3,0,2,-1,-1,-3,-4,-1,-3,-3,-1,0,-1,-4,-3,-3}, 
            { 0, -3, -3,  -3, 9 ,-3,-4,-3,-3,-1,-1,-3,-1,-2,-3,-1,-1,-2,-2,-1},
            { -1, 1,  0,  0, -3,   5 ,2,-2,0,-3,-2,1,0,-3,-1,0,-1,-2,-1,-2}, 
            { -1, 0,  0,  2, -4,   2, 5  ,-2,0,-3,-3,1,-2,-3,-1,0,-1,-3,-2,-2}, 
            { 0, -2,  0,  -1, -3, -2, -2,  6 ,-2,-4,-4,-2,-3,-3,-2,0,-2,-2,-3,-3},
            { -2, 0,  1,  -1, -3,  0, 0,  -2,  8 , -3,-3,-1,-2,-1,-2,-1,-2,-2,2,-3}, 
            { -1, -3, -3, -3, -1, -3, -3, -4, -3,  4, -2,-3,1,0,-3,-2,-1,-3,-1,3 },
            { -1, -2, -3, -4, -1, -2, -3, -4, -3, -2,  4  ,-2,2,0,-3,-2,-1,-2,-1,1}, 
            { -1, 2,  0,  -1, -3,  1, 1,  -2, -1, -3, -2,  5 ,-1,-3,-1,0,-1,-3,-2,-2},
            { -1, -1, -2, -3, -1,  0, -2, -3, -2,  1,  2, -1,  5  ,0,-2,-1,-1,-1,-1,1},
            { -2, -3, -3, -3, -2, -3, -3, -3, -1,  0,  0, -3,  0, 6  ,-4,-2,-2,1,3,-1},
            { -1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4, 7 ,-1,-1,-4,-3,-1},
            { 1, -1,   1,  0, -1,  0, 0,   0, -1, -2, -2, 0,  -1, -2, -1, 4 ,1,-3,-2,-2},
            { 0, -1,  0,  -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1, 1,  5 ,-2,-2,0},
            { -3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1,  1, -4, -3, -2, 11,2, -3 },
            { -2, -2, -2, -3, -2, -1, -2, -3,  2, -1, -1, -2, -1,  3, -3, -2, -2, 2, 7 ,-1},
            { 0, -3, -3,  -3, -1, -2, -2, -3, -3,  3,  1, -2,  1, -1, -2, -2, 0, -3, -1,4 }

    };
    static final int[][] nMatrix = { { 1, -5, -5, -1 }, { -5, 1, -1, -5 }, { -5, -1, 1, -5 }, { -1, -5, -5, 1 } };

    static final String dna = "ACGT";
    static final String rna = "ACGU";
    
    static final String aa = "ARNDCQEGHILKMFPSTWYV";
  
    static JTextArea out = new JTextArea();;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SmithWatermanGUI frame = new SmithWatermanGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SmithWatermanGUI() {
        setTitle("SW");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 791, 662);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JLabel lblS = new JLabel("s串地址：");
        
        JLabel lblT = new JLabel("t串地址：");
        
        s_adr = new JTextField();
        s_adr.setText("/Users/summerchaser/Desktop/ds.txt");
        s_adr.setColumns(10);
        
        t_adr = new JTextField();
        t_adr.setText("/Users/summerchaser/Desktop/dt.txt");
        t_adr.setColumns(10);
        
        JLabel label = new JLabel("打分原则：");
        
        JLabel label_1 = new JLabel("插入：");
        
        JLabel label_2 = new JLabel("删除：");
        
        JLabel label_3 = new JLabel("替换：");
        
        JLabel label_4 = new JLabel("匹配：");
        
        insert = new JTextField();
        insert.setText("-2");
        insert.setColumns(10);
        
        JLabel label_5 = new JLabel("数据读入");
        
        replace = new JTextField();
        replace.setText("0");
        replace.setColumns(10);
        
        delete = new JTextField();
        delete.setText("-2");
        delete.setColumns(10);
        
        match = new JTextField();
        match.setText("3");
        match.setColumns(10);
        
        JButton btnNewButton = new JButton("运行 （自定义打分规则）");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                out.setText("");
                StringBuffer s = new StringBuffer(); // s串
                StringBuffer t = new StringBuffer(); // t串
                StringBuffer ds = new StringBuffer(); // s串
                StringBuffer dt = new StringBuffer(); // s串

              
                    // 子串读入
                    InputStream fs = null;
                    try {
                        fs = new FileInputStream(s_adr.getText());
                        
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    InputStream ft = null;
                    try {
                        ft = new FileInputStream(t_adr.getText());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    int c;
                    try {
                        while ((c = fs.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                s.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        while ((c = ft.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                t.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    out.append("s串为： " + s+"\n");
                    out.append("t串为： " + t+"\n");

                    int m = s.length()+1;
                    int n = t.length()+1;
                   // System.out.println(m+" "+n);

                    int[][] M = new int[n][m];
                    char[][] S = new char[n][m]; //记录状态矩阵
                    int x1, x2, x3;
                    for (int i = 1; i < n; i++) {
                        for (int j = 1; j < m; j++) {
                            x1 = M[i][j-1] + Integer.valueOf(delete.getText());
                            x2 = M[i-1][j] + Integer.valueOf(insert.getText());
                            if (s.charAt(j - 1) == t.charAt(i - 1)) {
                                x3 = M[i - 1][j - 1] + Integer.valueOf(match.getText());
                            } else {
                                x3 = M[i - 1][j - 1] + Integer.valueOf(replace.getText());
                            }
                            ArrayList<Integer> status = new ArrayList<Integer>(Arrays.asList(x1, x2, x3,0));
                            M[i][j] = Collections.max(status);
                            int index = status.indexOf(M[i][j]);
                            if (index == 0) {
                                S[i][j] = '|';
                            }else if (index == 1) {
                                S[i][j] = '-';
                            }else if (index == 2) {
                                S[i][j] = '\\';
                            }else {
                                S[i][j] = '0';
                                
                                       
                            }
                        }
                    }
                    out.append("得分矩阵为：\n");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            out.append(String.format("%2d",M[i][j])+" ");
                        }
                        out.append("\n");
                    }
                    int i = n - 1; // t
                    int j = m - 1; // s
                    int max = -10000, max_x = 0, max_y = 0;
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            if (M[i][j] > max) {
                                max_x = i;
                                max_y = j;
                                max = M[i][j];
                            }
                        }
                    }
                    i = max_x;
                    j = max_y;
                    while (i != 0 && j != 0 || M[i][j] != 0) {
                        if (i == 0) {
                            dt.insert(0,"-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            continue;
                        }
                        if (j == 0) {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;
                            continue;

                        }
                        if (S[i][j] == '\\') {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            i--;
                        } else if (S[i][j] == '-') {
                            dt.insert(0, "-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                        } else {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;

                        }
                    }
                    out.append("\n");
                    out.append("比对结果序列为 ：\n");
                    out.append("ds串为： " + ds +"\n");
                    out.append("dt串为： " + dt+"\n");
                    out.append("\n");
                    out.append("状态转移矩阵为 ：\n");
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            out.append(S[i][j]+" ");
                        }
                        out.append("\n");
                    }
                    
                    try {
                        // 请在这修改文件输出路径
                        File fo = new File("/Users/summerchaser/Desktop/Sout.txt");
                        FileWriter fileWriter = new FileWriter(fo);
                        fileWriter.write(out.getText());
                        fileWriter.close(); // 关闭数据流
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                

                }
            
        });
        
        JLabel label_6 = new JLabel("运行结果：");
        
        JScrollPane scrollPane = new JScrollPane();
        
        JButton btnbla = new JButton("运行 （氨基酸序列 使用BLOSUM62打分）");
        btnbla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                

                out.setText("");
                StringBuffer s = new StringBuffer(); // s串
                StringBuffer t = new StringBuffer(); // t串
                StringBuffer ds = new StringBuffer(); // s串
                StringBuffer dt = new StringBuffer(); // s串

              
                    // 子串读入
                    InputStream fs = null;
                    try {
                        fs = new FileInputStream(s_adr.getText());
                        
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    InputStream ft = null;
                    try {
                        ft = new FileInputStream(t_adr.getText());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    int c;
                    try {
                        while ((c = fs.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                s.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        while ((c = ft.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                t.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    out.append("s串为： " + s+"\n");
                    out.append("t串为： " + t+"\n");

                    int m = s.length()+1;
                    int n = t.length()+1;
                   // System.out.println(m+" "+n);

                    int[][] M = new int[n][m];
                    char[][] S = new char[n][m]; //记录状态矩阵
                    int x1, x2, x3;
                    for (int i = 1; i < n; i++) {
                        for (int j = 1; j < m; j++) {
                            x1 = M[i][j - 1] - 2;//插入删除
                            x2 = M[i - 1][j] - 2;
                            char a = s.charAt(j - 1) ;
                            char b = t.charAt(i - 1);
                            int pj = aa.indexOf(a);
                            int pi = aa.indexOf(b);
                            x3 = M[i - 1][j - 1] + BLOSUM62[pi][pj];
                            ArrayList<Integer> status = new ArrayList<Integer>(Arrays.asList(x1, x2, x3, 0));
                            M[i][j] = Collections.max(status);
                            int index = status.indexOf(M[i][j]);
                            if (index == 0) {
                                S[i][j] = '-';
                            } else if (index == 1) {
                                S[i][j] = '|';
                            } else if (index == 2) {
                                S[i][j] = '\\';
                            } else {
                                S[i][j] = '0';

                            }
                        }
                    }
                    out.append("\n");
                    out.append("得分矩阵为：\n");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            out.append(String.format("%2d",M[i][j])+" ");
                        }
                        out.append("\n");
                    }
                    int i = n - 1; // t
                    int j = m - 1; // s
                    int max = -10000, max_x = 0, max_y = 0;
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            if (M[i][j] > max) {
                                max_x = i;
                                max_y = j;
                                max = M[i][j];
                            }
                        }
                    }
                    i = max_x;
                    j = max_y;
                    
                    while (i != 0 && j != 0 || M[i][j] != 0) {
                        if (i == 0) {
                            dt.insert(0,"-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            continue;
                        }
                        if (j == 0) {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;
                            continue;

                        }
                        if (S[i][j] == '\\') {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            i--;
                        } else if (S[i][j] == '-') {
                            dt.insert(0, "-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                        } else {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;

                        }
                    }
                    out.append("\n");
                    out.append("比对结果序列为 ：\n");
                    out.append("ds串为： " + ds +"\n");
                    out.append("dt串为： " + dt+"\n");
                    out.append("\n");
                    out.append("状态转移矩阵为 ：\n");
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            out.append(S[i][j]+" ");
                        }
                        out.append("\n");
                    }
                
                
            }
        });
        btnbla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        
        JButton btnrna = new JButton("运行 （RNA序列 使用转换颠换矩阵）");
        btnrna.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                out.setText("");
                StringBuffer s = new StringBuffer(); // s串
                StringBuffer t = new StringBuffer(); // t串
                StringBuffer ds = new StringBuffer(); // s串
                StringBuffer dt = new StringBuffer(); // s串

              
                    // 子串读入
                    InputStream fs = null;
                    try {
                        fs = new FileInputStream(s_adr.getText());
                        
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    InputStream ft = null;
                    try {
                        ft = new FileInputStream(t_adr.getText());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    int c;
                    try {
                        while ((c = fs.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                s.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        while ((c = ft.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                t.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    out.append("s串为： " + s+"\n");
                    out.append("t串为： " + t+"\n");

                    int m = s.length()+1;
                    int n = t.length()+1;
                   // System.out.println(m+" "+n);

                    int[][] M = new int[n][m];
                    char[][] S = new char[n][m]; //记录状态矩阵
                    int x1, x2, x3;
                  for (int i = 1; i < n; i++) {
                  for (int j = 1; j < m; j++) {
                      x1 = M[i][j - 1] - 2;//插入删除
                      x2 = M[i - 1][j] - 2;
                      char a = s.charAt(j - 1) ;
                      char b = t.charAt(i - 1);
                      int pj = rna.indexOf(a);
                      int pi = rna.indexOf(b);
                     System.out.println(nMatrix[pi][pj]);
                      x3 = M[i - 1][j - 1] + nMatrix[pi][pj];
                      ArrayList<Integer> status = new ArrayList<Integer>(Arrays.asList(x1, x2, x3, 0));
      
                      M[i][j] = Collections.max(status);
                      int index = status.indexOf(M[i][j]);
                      if (index == 0) {
                          S[i][j] = '-';
                      } else if (index == 1) {
                          S[i][j] = '|';
                      } else if (index == 2) {
                          S[i][j] = '\\';
                      } else {
                          S[i][j] = '0';
      
                      }
                  }
              }
                    out.append("\n");
                    out.append("得分矩阵为：\n");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            out.append(String.format("%2d",M[i][j])+" ");
                        }
                        out.append("\n");
                    }
                    int i = n - 1; // t
                    int j = m - 1; // s
                    int max = -10000, max_x = 0, max_y = 0;
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            if (M[i][j] > max) {
                                max_x = i;
                                max_y = j;
                                max = M[i][j];
                            }
                        }
                    }
                    i = max_x;
                    j = max_y;
                    
                    while (i != 0 && j != 0 || M[i][j] != 0) {
                        if (i == 0) {
                            dt.insert(0,"-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            continue;
                        }
                        if (j == 0) {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;
                            continue;

                        }
                        if (S[i][j] == '\\') {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            i--;
                        } else if (S[i][j] == '-') {
                            dt.insert(0, "-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                        } else {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;

                        }
                    }
                    out.append("\n");
                    out.append("比对结果序列为 ：\n");
                    out.append("ds串为： " + ds +"\n");
                    out.append("dt串为： " + dt+"\n");
                    out.append("\n");
                    out.append("状态转移矩阵为 ：\n");
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            out.append(S[i][j]+" ");
                        }
                        out.append("\n");
                    }
            }
        });
        
        JButton btndna = new JButton("运行 （DNA序列 使用转换颠换矩阵）");
        btndna.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                out.setText("");
                StringBuffer s = new StringBuffer(); // s串
                StringBuffer t = new StringBuffer(); // t串
                StringBuffer ds = new StringBuffer(); // s串
                StringBuffer dt = new StringBuffer(); // s串

              
                    // 子串读入
                    InputStream fs = null;
                    try {
                        fs = new FileInputStream(s_adr.getText());
                        
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    InputStream ft = null;
                    try {
                        ft = new FileInputStream(t_adr.getText());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    int c;
                    try {
                        while ((c = fs.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                s.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    try {
                        while ((c = ft.read()) != -1) {
                            Character m = new Character((char)c);
                            if (Character.isLetter(m)) {
                                t.append(m.toString()); //只有字母才能读入能略去空格之类
                            }
                        
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    out.append("s串为： " + s+"\n");
                    out.append("t串为： " + t+"\n");

                    int m = s.length()+1;
                    int n = t.length()+1;
                   // System.out.println(m+" "+n);

                    int[][] M = new int[n][m];
                    char[][] S = new char[n][m]; //记录状态矩阵
                    int x1, x2, x3;
                  for (int i = 1; i < n; i++) {
                  for (int j = 1; j < m; j++) {
                      x1 = M[i][j - 1] - 2;//插入删除
                      x2 = M[i - 1][j] - 2;
                      char a = s.charAt(j - 1) ;
                      char b = t.charAt(i - 1);
                      int pj = dna.indexOf(a);
                      int pi = dna.indexOf(b);
                     System.out.println(nMatrix[pi][pj]);
                      x3 = M[i - 1][j - 1] + nMatrix[pi][pj];
                      ArrayList<Integer> status = new ArrayList<Integer>(Arrays.asList(x1, x2, x3, 0));
      
                      M[i][j] = Collections.max(status);
                      int index = status.indexOf(M[i][j]);
                      if (index == 0) {
                          S[i][j] = '-';
                      } else if (index == 1) {
                          S[i][j] = '|';
                      } else if (index == 2) {
                          S[i][j] = '\\';
                      } else {
                          S[i][j] = '0';
      
                      }
                  }
              }
                    out.append("\n");
                    out.append("得分矩阵为：\n");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            out.append(String.format("%2d",M[i][j])+" ");
                        }
                        out.append("\n");
                    }
                    int i = n - 1; // t
                    int j = m - 1; // s
                    int max = -10000, max_x = 0, max_y = 0;
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            if (M[i][j] > max) {
                                max_x = i;
                                max_y = j;
                                max = M[i][j];
                            }
                        }
                    }
                    i = max_x;
                    j = max_y;
                    
                    while (i != 0 && j != 0 || M[i][j] != 0) {
                        if (i == 0) {
                            dt.insert(0,"-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            continue;
                        }
                        if (j == 0) {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;
                            continue;

                        }
                        if (S[i][j] == '\\') {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                            i--;
                        } else if (S[i][j] == '-') {
                            dt.insert(0, "-");
                            ds.insert(0, s.charAt(j - 1));
                            j--;
                        } else {
                            dt.insert(0, t.charAt(i - 1));
                            ds.insert(0, "-");
                            i--;

                        }
                    }
                    out.append("\n");
                    out.append("比对结果序列为 ：\n");
                    out.append("ds串为： " + ds +"\n");
                    out.append("dt串为： " + dt+"\n");
                    out.append("\n");
                    out.append("状态转移矩阵为 ：\n");
                    for (i = 0; i < n; i++) {
                        for (j = 0; j < m; j++) {
                            out.append(S[i][j]+" ");
                        }
                        out.append("\n");
                    }
            
            }
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(31)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addComponent(lblS)
                                    .addGap(18)
                                    .addComponent(s_adr, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                            .addComponent(lblT, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18)
                                            .addComponent(t_adr, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                            .addComponent(label_6)
                                            .addGap(18)
                                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(btnbla, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnrna, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btndna, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                            .addGap(31))
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addComponent(label_5)
                            .addPreferredGap(ComponentPlacement.RELATED)))
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                .addComponent(label_3)
                                .addComponent(label_1))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(insert, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(replace, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18)
                                    .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                            .addComponent(label_2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.UNRELATED)
                                            .addComponent(delete, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                            .addComponent(label_4)
                                            .addPreferredGap(ComponentPlacement.UNRELATED)
                                            .addComponent(match, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))))
                                .addComponent(btnNewButton))
                            .addGap(107))
                        .addComponent(label))
                    .addContainerGap(91, Short.MAX_VALUE))
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(46)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 681, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(54, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
            gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addComponent(label)
                        .addComponent(label_5))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                            .addComponent(label_1)
                            .addComponent(insert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_2)
                            .addComponent(delete, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(s_adr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblS))
                    .addGap(18)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(replace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_3)
                        .addComponent(label_4)
                        .addComponent(match, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(t_adr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblT))
                    .addGap(23)
                    .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_6)
                        .addComponent(btnbla)
                        .addComponent(btnNewButton))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnrna)
                    .addGap(5)
                    .addComponent(btndna)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                    .addGap(33))
        );
        
        
        scrollPane.setViewportView(out);
        contentPane.setLayout(gl_contentPane);
    }
}
