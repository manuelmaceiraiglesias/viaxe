//o open diante mellor que evita problemas en hibernate
open module dam.viaxe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires jakarta.persistence;


    //opens dam.viaxe to javafx.fxml;
    exports dam.viaxe;
}