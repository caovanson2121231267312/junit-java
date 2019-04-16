package giaodienchuan.model.FrontEnd.FormChon;

import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ChonSanPhamForm extends JFrame {

    QuanLySanPhamBUS qlsp = new QuanLySanPhamBUS();
    MyTable mtb;
    JTextField txTarget;

    JTextField txTim = new JTextField(15);
    JComboBox<String> cbTypeSearch;

    JButton btnOK = new JButton("Chọn");
    JButton btnCancel = new JButton("Thoát");

    final int MASP_I = 1;

    public ChonSanPhamForm(JTextField _txTarget) {
        this.setLayout(new BorderLayout());
        this.setTitle("Chọn Sản Phẩm");
        this.txTarget = _txTarget;

        mtb = new MyTable();
        mtb.setPreferredSize(new Dimension(1200 - 250, 400));
        mtb.setHeaders(new String[]{"STT", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá (triệu)", "Số lượng"});
        mtb.setColumnsWidth(new double[]{.5, 2, 2, 3, 2, 1});
        mtb.setAlignment(0, JLabel.CENTER);
        mtb.setAlignment(4, JLabel.RIGHT);
        mtb.setAlignment(5, JLabel.CENTER);

        // ======== Get Data from DB =======
        setDataToTable(qlsp.getDssp(), mtb);

        // ======== search panel ===========
        JPanel plTim = new JPanel();
        plTim.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txTim.setBorder(BorderFactory.createTitledBorder(" ")); // tạo border rỗng
        plTim.add(txTim);
        cbTypeSearch = new JComboBox<>(new String[]{"Tất cả", "Mã sản phẩm", "Mã loại", "Tên", "Đơn giá", "Số lượng"});
        plTim.add(cbTypeSearch);

        // ======= Buttons Panel ===========
        btnCancel.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_cancel_30px_1.png")));
        btnOK.setIcon(new ImageIcon(this.getClass().getResource("/giaodienchuan/images/icons8_ok_30px.png")));
        
        JPanel plBtns = new JPanel();
        plBtns.add(btnOK);
        plBtns.add(btnCancel);

        // add to this frame
        JPanel plContainer = new JPanel();
        plContainer.add(plTim);
        plContainer.add(mtb);
        plContainer.add(plBtns);
        
        this.add(plContainer, BorderLayout.CENTER);
        this.setSize(1200 - 200, 600);
        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        // actionlistener
        cbTypeSearch.addActionListener((ActionEvent e) -> {
            txTim.requestFocus();
            if (!txTim.getText().equals("")) {
                txSearchOnChange();
            }
        });

        btnOK.addActionListener((ActionEvent ae) -> {
            int i = mtb.getTable().getSelectedRow();
            if (i >= 0) {
                String masp = mtb.getModel().getValueAt(i, MASP_I).toString();
                this.txTarget.setText(masp);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm nào!");
            }
        });
        
        btnCancel.addActionListener((ae) -> {
            this.dispose();
        });

        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        txTim.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txSearchOnChange();
            }
        });
    }

    private void txSearchOnChange() {
        setDataToTable(qlsp.search(txTim.getText(), cbTypeSearch.getSelectedItem().toString()), mtb);
    }

    private void setDataToTable(ArrayList<SanPham> data, MyTable table) {
        table.clear();
        int stt = 1; // lưu số thứ tự dòng hiện tại
        for (SanPham sp : data) {
            table.addRow(new String[]{String.valueOf(stt), sp.getMaSP(), sp.getMaLSP(), sp.getTenSP(),
                String.valueOf(sp.getDonGia()), String.valueOf(sp.getSoLuong())});
            stt++;
        }
    }
}
