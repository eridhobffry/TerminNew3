package Data.manager;

import DAO.NutzerDAO;
import Data.Nutzer;


import java.util.List;

public class NutzerManager {

    private NutzerDAO nutzerDAO = new NutzerDAO();

    public List<Nutzer> findAll() {
        return nutzerDAO.findAll();
    }

    public Nutzer addNutzer (Nutzer nutzer){
        return nutzerDAO.create(nutzer);
    }
}
