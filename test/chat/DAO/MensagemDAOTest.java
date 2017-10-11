package chat.DAO;

import DAO.MensagemDAO;
import Domain.Mensagem;
import java.util.ArrayList;
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
            Mensagem mensagem = new Mensagem("Olá amiguinhos!", 1, 1);
            MensagemDAO instance = new MensagemDAO();
            boolean expResult = true;
            boolean result = instance.Envia_Msg(mensagem);
            assertEquals(expResult, result);
        } catch (Exception e) {
            fail("Mensagem Não Enviada");
        }
    }
    
    @Test
    public void testMostra_Msg() throws Exception {
        try {
            MensagemDAO instance = new MensagemDAO();
            ArrayList<Mensagem> result = instance.Mostra_Msg(1);
            for(int i = 0; i<result.size(); i++){
                System.out.println(result.get(i).getTxtMensagem());
                System.out.println(result.get(i).getUsuario());
                System.out.println(result.get(i).getDateTime());
                System.out.println(result.get(i).getIdSala());
                System.out.println("\n");
            }
        } catch (Exception e) {
            fail("Falha ao mostrar mensagem");
        }
    }

}
