package com.example.vidam.vidam;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServicosManager implements Serializable {
    public static ArrayList<Servico> servicos = new ArrayList<Servico>();
    public static ArrayList<Intervencao> localIntervencoes = new ArrayList<Intervencao>();
    public static ArrayList<Componente> componentes = new ArrayList<>();
    public static Integer funcionarioID = 1;


    public ServicosManager() {
    }

    public static ArrayList<Servico> getServicos() {
        return servicos;
    }

    public static void setServicos(ArrayList<Servico> servicos) {
        ServicosManager.servicos = servicos;
    }
    public static void load(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("ServicosManager");
            ObjectInputStream is = new ObjectInputStream(fis);
            servicos = (ArrayList<Servico>) is.readObject();
            is.close();
            fis.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void save(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("ServicosManager", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(ServicosManager.servicos);
            os.close();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static int createIntervencao(int servicoindex){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Intervencao i = new Intervencao(-1,"","08:00", "17:00",dateFormat.format(cal.getTime()),'N',funcionarioID,  new ArrayList<Componente>());
        i.setIdServico(servicos.get(servicoindex).getIdServico());
        ArrayList<TipoServico> clone = new ArrayList<>();
        for(TipoServico t: servicos.get(servicoindex).getTiposServico()) {
            TipoServico ts = (TipoServico) deepClone(t);
            ts.getEquipamento().initProcedimentos();
            ts.getEquipamento().clearEstados();
            clone.add(ts);
        }
        i.setTiposServico(clone);
        localIntervencoes.add(i);
        return localIntervencoes.indexOf(i);
    }

    public static ArrayList<Intervencao> getIntervencoesByServicoID(int servicoID){
       ArrayList<Intervencao> temp = new ArrayList<Intervencao>();
       for(Intervencao i : localIntervencoes){
           if(i.getIdServico() == servicoID)
               temp.add(i);
       }
       return temp;
    }
    public static void saveLocal(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("LocalIntervencoes", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(localIntervencoes);
            os.close();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadLocal(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("LocalIntervencoes");
            ObjectInputStream is = new ObjectInputStream(fis);
            localIntervencoes = (ArrayList<Intervencao>) is.readObject();
            is.close();
            fis.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void loadComponentes(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("Componentes");
            ObjectInputStream is = new ObjectInputStream(fis);
            componentes = (ArrayList<Componente>) is.readObject();
            is.close();
            fis.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveComponentes(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("Componentes", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(ServicosManager.componentes);
            os.close();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFunc(Context context){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput("FuncID");
            ObjectInputStream is = new ObjectInputStream(fis);
            funcionarioID = (int) is.readObject();
            is.close();
            fis.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void saveFunc(Context context){
        try {
            FileOutputStream fos = context.openFileOutput("FuncID", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(ServicosManager.funcionarioID);
            os.close();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deepClone(Object o){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(o);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(bais);
            return objectInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
