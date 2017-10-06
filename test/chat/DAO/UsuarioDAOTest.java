package chat.DAO;

import chat.Domain.Usuario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bella
 */
public class UsuarioDAOTest {

    public UsuarioDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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
            Usuario usuario = new Usuario("Gabrigol", "123456");
            Usuario usuario2 = new Usuario("Lolzinho", "123456");
            UsuarioDAO instance = new UsuarioDAO();
            String expResult = usuario2.getNomeusuario();
            String result = instance.cadastrar(usuario2);
            instance.cadastrar(usuario);
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
            UsuarioDAO instance = new UsuarioDAO();
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
            String senha = "123456";
            UsuarioDAO instance = new UsuarioDAO();
            Usuario expResult = new Usuario("Gabrigol", "123456");
            Usuario result = instance.getUserLogin(nome, senha);
            assertEquals(expResult.getNomeusuario(), result.getNomeusuario());
        } catch (Exception e) {
            fail("Não retornou usuario");
        }
    }

}
