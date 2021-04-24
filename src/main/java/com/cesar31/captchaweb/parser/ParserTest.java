package com.cesar31.captchaweb.parser;

import java.io.IOException;
import java.io.StringReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class ParserTest {

    public static void main(String[] args) {
        String title = "<C_TITLE>t   Mi_primer_titulo Mi primer titulo   </ C_TITLE >";
        String link = "<C_LINK\n"
                + "!! El link al que redirige mi captcha\n"
                + "[href= \"https://www.mclibre.org/consultar/htmlcss/html/html-etiquetas.html\"]>\n"
                + "</C_LINK>";

        String link2 = " < C_LINK [href=\"https://www.mclibre.org/consultar/htmlcss/html/html-etiquetas.html\"]>< /  C_LINK\n>";

        String spam = "<C_SPAM [id= \"mostrar_1\"] [text-align= \"center\"] [color= \"#3366ff\"]>\n"
                + "¿ Qué resultado genera la operación siguiente: 5+5 ?\n"
                + "</C_SPAM>";

        String h1 = "<C_H1 [id= \"title_1\"] [text-align= \"center\"] [color= \"#7eff33\"] >\n"
                + "Mi primer Captcha Matemático\n"
                + "</C_H1>";

        String button = "!! Boton que llama a la funcionalidad calc\n"
                + "<C_BUTTON [id= \"boton_1\"] [onclick= \"PROCESS_calc()\"] [background=\"green\"]>\n"
                + "Procesar Procesando\n"
                + "</C_BUTTON>";

        String p = "<C_p [id= \"paragraph_1\"] [text-align= \"center\"] [color= \"#7eff33\"] >\n"
                + "Mi primer parrafo!\n"
                + "</C_P>";

        String img = "<C_IMG [src=\"https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/bojack-horseman-final-1571059533.jpg?crop=0.927xw:0.925xh;0.0538xw,0.0754xh&resize=2048:*\"]"
                + " [width=\"50%\"] [height=\"360px\"] [alt=\"Imagen 1\"] [id=\"id__img_1\"] ></C_IMG>";

        String input = "<  C_INPUT [type= \"text\"] [text-align= \"center\"] [id= \"entrada_1\"] ></ c_INPUT  >";

        String txtArea = "<c_TEXTAREA [font-size = \"16px\"] [font-family = \"Courier\"] [text-align=\"left\"] [id=\"txt_1\"] [cols=\"20\"] [rows=\"10\"] ></C_TEXTAREA>";

        String option = "<C_OPTION> Mi opcion </C_OPTION>";

        String select = "<C_SELECT [font-size = \"16px\"] [font-family = \"Courier\"] [text-align=\"left\"] [id=\"select_1\"] [color = \"green\"]>\n"
                + "<C_OPTION>Opcion no1</C_OPTION>\n"
                + "<C_OPTION>Opcion no2</C_OPTION>\n"
                + "<C_OPTION>Opcion no3</C_OPTION>\n"
                + "<C_OPTION>Opcion no4</C_OPTION>\n"
                + "\n</C_SELECT>";

        String div = "<C_DIV [font-size = \"16px\"] [font-family = \"Courier\"] [text-align=\"left\"] [id=\"select_1\"] [color = \"green\"] "
                + " [background=\"#f0fabc\"] [class=\"row\"] ></C_DIV>";

        String head = "    <C_HEAD>\n"
                + "    !! El título de mi página\n"
                + "    <C_TITLE> Mi titulo no_1</C_TITLE>\n"
                + "    <C_LINK\n"
                + "    !! El link al que redirige mi captcha\n"
                + "    [href= \"https://www.mclibre.org/consultar/htmlcss/html/html-etiquetas.html\"]>\n"
                + "    </C_LINK>\n"
                + "    \n"
                + "\n"
                + "    </C_HEAD>";

        // Cambiar string aqui
        String string = head;

        System.out.println(string);
        //getTokens(string);
        System.out.println("\n");

        CaptchaLex lex = new CaptchaLex(new StringReader(string));
        CaptchaParser parser = new CaptchaParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void getTokens(String input) {
        CaptchaLex lex = new CaptchaLex(new StringReader(input));
        while (true) {
            Symbol s;
            try {
                s = lex.next_token();
                if (s.sym == 0) {
                    break;
                }
                System.out.println("Fila " + s.left + " Columna: " + s.right + " Value: " + s.value + ", Token: " + s.sym);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
