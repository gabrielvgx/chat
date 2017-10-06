package chat.DAO;

import chat.Domain.Mensagem;
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
public class MensagemDAOTest {

    public MensagemDAOTest() {
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
     * Test of Envia_Msg method, of class MensagemDAO.
     */
    @Test
    public void testEnvia_Msg() throws Exception {
        try {
            Mensagem mensagem = new Mensagem("Olá amiguinhos!", "Gabrigol");
            MensagemDAO instance = new MensagemDAO();
            boolean expResult = true;
            boolean result = instance.Envia_Msg(mensagem);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Mensagem Não Enviada");
        }
    }

}
