/**
 * Core module for the Internship Placement Management System (IPMS). 
 * It manages all data entities, persistence logic, and views for users, 
 * companies, internships, and applications.
 */
module sc2002OOP {
	exports sc2002OOP.main; 
    
	/**
     * Base object package
     */
    exports sc2002OOP.obj;
    
    /**
     * Data entities and managers for Career Center Staff users
     */
    exports sc2002OOP.obj.careercenterstaff;
    
    /**
     * Data entities and managers for Company information
     */
    exports sc2002OOP.obj.company;
    
    /**
     * Data entities and managers for Company Representative users
     */
    exports sc2002OOP.obj.companyrepresentative;
    
    /**
     * Data entities, managers, and views for Internship Applications
     */
    exports sc2002OOP.obj.internshipapplicaton;
    
    /**
     * Data entities, managers, and views for Internship Opportunities
     */
    exports sc2002OOP.obj.internshipopportunity;
    
    /**
     * Data entities, managers, and views for Student users
     */
    exports sc2002OOP.obj.student;
    
    /*
     * Data entities, managers, and views for Internship Withdrawal Requests
     */
    exports sc2002OOP.obj.withdrawalrequest;
}