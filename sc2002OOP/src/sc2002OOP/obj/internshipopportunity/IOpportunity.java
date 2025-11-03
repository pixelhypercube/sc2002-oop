package sc2002OOP.obj.internshipopportunity;

import java.time.LocalDate;
import java.util.ArrayList;

import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;

public interface IOpportunity {
		void print();
		void printForStudent();
		
		String getInternshipID();
		void setInternshipID(String internshipID);
		
		String getTitle();
		void setTitle(String title);
		
		String getDescription();
		void setDescription(String description);
		
		String getCompanyID();
		void setCompanyID(String companyID);
		
		String getPreferredMajor();
		void setPreferredMajor(String preferredMajor);
		
		ArrayList<CompanyRepresentative> getCompanyRepsInCharge();
		void setCompanyRepsInCharge(ArrayList<CompanyRepresentative> companyRepInCharge);
		
		LocalDate getOpeningDate();
		void setOpeningDate(LocalDate openingDate);
		
		LocalDate getClosingDate();
		void setClosingDate(LocalDate closingDate);
		
		int getNumSlots();
		void setNumSlots(int numSlots);
		
		InternshipOpportunityLevel getLevel();
		void setLevel(InternshipOpportunityLevel level);
		
		InternshipOpportunityStatus getStatus();
		void setStatus(InternshipOpportunityStatus status);
		
		boolean isVisibility();
		void setVisibility(boolean visibility);
}
