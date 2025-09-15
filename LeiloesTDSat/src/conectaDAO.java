import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class conectaDAO {

    public Connection connectDB() {
        Connection conn = null;

        try {
            // Carrega o driver MySQL explicitamente
            Class.forName("com.mysql.cj.jdbc.Driver");

            // String de conexão com serverTimezone para evitar warnings/falhas
            String url = "jdbc:mysql://localhost:3306/uc11?useSSL=false&verifyServerCertificate=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            conn = DriverManager.getConnection(url, "root", "MOC;1993");

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver MySQL não encontrado: " + e.getMessage());
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ConectaDAO: " + erro.getMessage());
        }
        return conn;
    }

    public void testeConexao() {
        try (Connection conn = connectDB()) {
            if (conn != null) {
                System.out.println("Conexão OK!");
            } else {
                System.out.println("Falha ao conectar.");
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public boolean venderProduto(int id) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ? AND status = 'A Venda'";

        try (Connection conn = this.connectDB();
             PreparedStatement prep = conn.prepareStatement(sql)) {

            prep.setString(1, "Vendido");
            prep.setInt(2, id);

            int linhasAfetadas = prep.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
            return false;
        }
    }
}
