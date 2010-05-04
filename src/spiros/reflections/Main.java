package spiros.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {

        java.lang.String[] s = new String[] { "2", "sdf" };

        // System.out.println("Class is: "+s.getClass().getName());

        try
        {
            Class<?> c = Class.forName(Boolean.class.getName());

            Constructor<?> dd = c.getConstructor(String.class);
            Object obj = dd.newInstance("true");

            // Constructor<?>[] constr = c.getConstructors();
            // for (int i = 0; i < constr.length; i++)
            // {
            // System.out.println("Constuctor: " + constr[i].getName());
            // for (int j = 0; j < constr[i].getParameterTypes().length; j++)
            // {
            // if
            // (constr[i].getParameterTypes()[j].getName().equals("java.lang.String"))
            // {
            // System.out.println("    " +
            // constr[i].getParameterTypes()[j].getName());
            // obj = constr[i].newInstance("true");
            // break;
            // }
            // }
            // }

            System.out.println("Class is: " + obj.getClass().getName());

            // Object sd = Array.newInstance(c, 3);

            // System.out.println("Class is: "+sd.getClass().getName());

            String type = "ArrayOf_xsd_anyType";
            String[] d = type.split("_");

            System.out.println("d: " + d[d.length - 1]);

            // for(int i=0;i<d.length;i++){
            // System.out.println("d: "+d[i]);
            // }

        }
        catch (ClassNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoSuchMethodException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
