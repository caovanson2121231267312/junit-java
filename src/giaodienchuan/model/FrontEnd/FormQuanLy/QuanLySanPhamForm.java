package giaodienchuan.model.FrontEnd.FormQuanLy;

import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.WorkWithExcel.XuatExcel;
import giaodienchuan.model.FrontEnd.FormHienThi.HienThiSanPham;
import giaodienchuan.model.FrontEnd.FormThemSua.ThemSuaSanPhamForm;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.LoginForm;
import giaodienchuan.model.FrontEnd.MyButton.ExportExcelButton;
import giaodienchuan.model.FrontEnd.MyButton.SuaButton;
import giaodienchuan.model.FrontEnd.MyButton.ThemButton;
import giaodienchuan.model.FrontEnd.MyButton.XoaButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuanLySanPhamForm extends JPanel {

    HienThiSanPham formHienThi = new HienThiSanPham();

    ThemButton btnThem = new ThemButton();
    SuaButton btnSua = new SuaButton();
    XoaButton btnXoa = new XoaButton();
    ExportExcelButton btnXuatExcel = new ExportExcelButton();

    public QuanLySanPhamForm() {
        setLayout(new BorderLayout());

        // buttons
        if (!LoginForm.quyenLogin.getChiTietQuyen().contains("qlSanPham")) {
            btnThem.setEnabled(false);
            btnXoa.setEnabled(false);
            btnSua.setEnabled(false);
        }

        JPanel plBtn = new JPanel();
        plBtn.add(btnThem);
        plBtn.add(btnXoa);
        plBtn.add(btnSua);
        plBtn.add(btnXuatExcel);

        this.add(formHienThi, BorderLayout.CENTER);
        this.add(plBtn, BorderLayout.NORTH);

        // actionlistener
        btnThem.addActionListener((ActionEvent ae) -> {
            btnThemMouseClicked();
        });
        btnXoa.addActionListener((ActionEvent ae) -> {
            btnXoaMouseClicked();
        });
        btnSua.addActionListener((ActionEvent ae) -> {
            btnSuaMouseClicked();
        });
        btnXuatExcel.addActionListener((ActionEvent ae) -> {
            try {
                new XuatExcel().xuatFileExcelSanPham();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lỗi khi xuất file excel!" + e.getMessage());
            }
        });
    }

    private void btnSuaMouseClicked() {
        String masp = formHienThi.getSelectedRow(1);
        if (masp != null) {
            ThemSuaSanPhamForm suasp = new ThemSuaSanPhamForm("Sửa", masp);

            // https://stackoverflow.com/questions/4154780/jframe-catch-dispose-event
            suasp.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    formHienThi.refresh();
                }
            });

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để sửa");
        }
    }

    private void btnXoaMouseClicked() {
        String masp = formHienThi.getSelectedRow(1);
        if (masp != null) {
            if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa sản phẩm " + masp + " ? "
                    + "Sản phẩm sẽ được Ẩn", "Chú ý", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                new QuanLySanPhamBUS().updateTrangThai(masp, 1);
                formHienThi.refresh();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn sản phẩm nào để xóa");
        }
    }

    private void btnThemMouseClicked() {
        ThemSuaSanPhamForm themsp = new ThemSuaSanPhamForm("Thêm", "");
        themsp.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                formHienThi.refresh();
            }
        });
    }
}
