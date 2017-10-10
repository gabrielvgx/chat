package chat.DAO;

import DAO.UsuarioDAO;
import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author bella
 */
public class UsuarioDAOTest {
        private static UsuarioDAO instance;

    public UsuarioDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
            try {
                instance = new UsuarioDAO();
                Usuario gvgx = new Usuario("Gabrigol", null, false, 2);
                instance.cadastrar(gvgx);
            } catch (ExcecaoPersistencia ex) {
                Logger.getLogger(UsuarioDAOTest.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of cadastrar method, of class UsuarioDAO.
     */
    @Test
    public void testCadastrar() throws Exception {
        try {
            Usuario usuario2 = new Usuario("Lolzinho", null, false, 2);
            String expResult = usuario2.getNomeUsuario();
            String result = instance.cadastrar(usuario2);
            //instance.cadastrar(usuario);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Não gravou");
        }
    }

    /**
     * Test of excluir method, of class UsuarioDAO.
     */
    @Test
    public void testExcluir() throws Exception {
        try {
            String usuario = "Lolzinho";
            boolean expResult = true;
            boolean result = instance.excluir(usuario);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Não deletou");
        }
    }

    /**
     * Test of getUserLogin method, of class UsuarioDAO.
     */
    @Test
    public void testGetUserLogin() throws Exception {
        try {
            String nome = "Gabrigol";
            String senha = null;
            Usuario expResult = new Usuario("Gabrigol", null, false, 2);
            Usuario result = instance.getUserLogin(nome, senha);
            assertEquals(expResult.getNomeUsuario(), result.getNomeUsuario());
        } catch (Exception e) {
            fail("Não retornou usuario");
        }
    }

}
