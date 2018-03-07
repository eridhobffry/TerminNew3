package Data.manager;

import DAO.NutzerDAO;
import Data.Nutzer;


import java.util.List;

public class NutzerManager {

    private NutzerDAO nutzerDAO = new NutzerDAO();

    public List<Nutzer> findAll() {
        return nutzerDAO.findAll();
    }

    public Boolean addNutzer (String vorname, String nachname){
        return nutzerDAO.create(vorname, nachname);
    }
}
