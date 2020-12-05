/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeladorrelacional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.JTable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author rsoto
 */
public class ERDParser {

    final File erdFile;

    //public Hashtable<String, ArrayList> entAtribs;
    public Hashtable<String, JTable> entTablas;
    public ArrayList<String> listaEnts;
    //public ArrayList<String> listaRelaciones;

    private Object[] columnNames = {"Nombre", "TIpo", "Tamaño", "Precisión", "Not NULL?", "Primary Key"};

    public ERDParser(File erdFile) {
        this.erdFile = erdFile;
    }

    public void parseERD() throws FileNotFoundException {
        FileReader fp = new FileReader(erdFile);

        // Crear el tokenizador del documento JSON  
        JSONTokener tokenizer = new JSONTokener(fp);

        // Objeto principal que contiene todos los componentes
        // del diagrama ERD
        JSONObject JSONDoc = new JSONObject(tokenizer);

        //Obtenet los nombres de los objetos 
        JSONArray names = JSONDoc.names();
        System.out.println(names);

        // Solo recuperar los objetos que describen entidades fuertes
        JSONArray entidades = JSONDoc.getJSONArray("entidades");
        System.out.println(entidades);

        Iterator it = entidades.iterator();

        listaEnts = new ArrayList<>();
        //entAtribs = new Hashtable<>();
        entTablas = new Hashtable<>();

        System.out.println("***** ENTIDADES *****");
        // Procesar cada una de las entidades

        String atribName;

        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();

            // Para cada entidad, mostrar su nombre
            String entityName = entidad.getString("nombre");
            System.out.println(entityName);

            listaEnts.add(entityName);

            // Para cada entidad, mostrar los atributos
            JSONArray atributos = entidad.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();
            //String atribName;

            //ArrayList<String> atribsF = new ArrayList<>();

            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                atribName = atributo.getString("nombre");
                System.out.print(atribName);
                //System.out.print(atributo.getString("nombre"));

                if (atributo.getInt("tipo") == 1) {
                    System.out.println(" *");
                    atribName += "*";
                } else {
                    System.out.println();
                }
                //atribsF.add(atribName);
            }
            //entAtribs.put(entityName, atribsF);

            attribIt = atributos.iterator();

            Object[][] data = new Object[atributos.length()][6];

            for (int i = 0; i < atributos.length(); i++) {
                JSONObject atributo = (JSONObject) attribIt.next();

                data[i][0] = atributo.getString("nombre");

                data[i][4] = new Boolean(false);

                if (atributo.getInt("tipo") == 1) {
                    data[i][5] = new Boolean(true);
                } else {
                    data[i][5] = new Boolean(false);
                }
            }
            JTable tabla = new JTable(data, columnNames);
            entTablas.put(entityName, tabla);
        }

        System.out.println("--------------------------------------------------");

        // Sólo recuperar los objetos que describan las entidadades débiles
        JSONArray debiles = JSONDoc.getJSONArray("debiles");
        System.out.println(debiles);

        it = debiles.iterator();

        System.out.println("***** ENTIDADES DÉBILES *****");

        // Procesar cada una de las entidades débiles
        while (it.hasNext()) {

            JSONObject debil = (JSONObject) it.next();

            // Para cada entidad débil, mostrar su nombre
            String entityName = debil.getString("nombre");
            System.out.println(entityName);

            listaEnts.add(entityName);

            String entidadFuerte = debil.getString("fuerte");
            System.out.println("\tEntidad Fuerte: " + entidadFuerte);

            // Para cada entidad, mostrar los atributos
            JSONArray atributos = debil.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();

            //ArrayList<String> atribsD = new ArrayList<>();
            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                atribName = atributo.getString("nombre");
                System.out.print(atribName);

                if (atributo.getInt("tipo") == 1) {
                    System.out.print(" *");
                } else {
                    System.out.println();
                }
                //atribsD.add(atribName);
            }
            //entAtribs.put(entityName, atribsD);

            attribIt = atributos.iterator();

            Object[][] data = new Object[atributos.length()][6];

            for (int i = 0; i < atributos.length(); i++) {
                JSONObject atributo = (JSONObject) attribIt.next();

                data[i][0] = atributo.getString("nombre");

                data[i][4] = new Boolean(false);

                if (atributo.getInt("tipo") == 1) {
                    data[i][5] = new Boolean(true);
                } else {
                    data[i][5] = new Boolean(false);
                }

                JTable tabla = new JTable(data, columnNames);
                entTablas.put(entityName, tabla);
            }
        }

        System.out.println("--------------------------------------------------");

        // Solo recuperar los objetos que describen relaciones
        JSONArray relations = JSONDoc.getJSONArray("relaciones");

        System.out.println("**** RELACIONES ****");

        it = relations.iterator();

        while (it.hasNext()) {
            JSONObject rel = (JSONObject) it.next();

            System.out.println(rel.getString("nombre"));

            JSONArray cards = rel.getJSONArray("cardinalidades");

            int n = cards.length();

            for (int i = 0; i < n; i++) {
                JSONObject e1 = cards.getJSONObject(i);

                System.out.printf("\t%s (%s,%s)\n", e1.getString("entidad"),
                        e1.getString("min"),
                        e1.getString("max"));

            }

        }
    }
}
