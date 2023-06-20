module project_partc.atpprojectpartc {
    requires javafx.controls;
    requires javafx.fxml;
    requires ATP.Project.PartB;
    requires javafx.media;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens project_partc.atpprojectpartc.Model to javafx.fxml;
    opens project_partc.atpprojectpartc.View to javafx.fxml;
    opens project_partc.atpprojectpartc.ViewModel to javafx.fxml;

    exports project_partc.atpprojectpartc.Model;
    exports project_partc.atpprojectpartc.View;
    exports project_partc.atpprojectpartc.ViewModel;
}