package com.search.ontology;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.SimpleIRIMapper;


/**
 * This class i will define some method to access to ontology, individual and object properties.
 * 
 * @author HoangAnh
 * 
 * @created 2014-09-09
 *
 */
public class OntologyManager {
	//define computer ontology
	private static final String COMPUTER_ONTOLOGY_FILE = "D:\\Java\\workspace\\search-ph\\ontology\\computer-ontology.owl";
	
	//define object properties.
	private static final String IS_INDIVIDUAL_OF = "http://www.semanticweb.org/anhh1/ontologies/2014/1/untitled-ontology-13#is_individual_of";
	private static final String IS_PART_OF 		= "http://www.semanticweb.org/anhh1/ontologies/2014/1/untitled-ontology-13#is_part_of";
	private static final String IS_PRODUCT_OF   = "http://www.semanticweb.org/anhh1/ontologies/2014/1/untitled-ontology-13#is_product_of";
	//end define object properties
	
	private static final List<String> OBJECT_PROPERTIES_LIST = new ArrayList<String>();
	
	private static OWLOntologyManager manager 	= null;
	private static OWLOntology ont 				= null;
	private static OWLDataFactory fac 			= null;
	private static OWLReasoner reasoner 		= null;
	
	static {
		System.out.println("================================== INIT ONTOLOGY ============");
		OBJECT_PROPERTIES_LIST.add(IS_INDIVIDUAL_OF);
		OBJECT_PROPERTIES_LIST.add(IS_PART_OF);
		OBJECT_PROPERTIES_LIST.add(IS_PRODUCT_OF);
		manager 					= OWLManager.createOWLOntologyManager();
		File file 					= new File(COMPUTER_ONTOLOGY_FILE);
		fac 						= manager.getOWLDataFactory();
		try {
			ont 							= manager.loadOntologyFromOntologyDocument(file);
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			System.out.println("=============================== INIT ONTOLOGY HAS EXCEPTION ============");
		}
		OWLReasonerFactory reasonerFactory 			= new StructuralReasonerFactory();
		ConsoleProgressMonitor progressMonitor 		= new ConsoleProgressMonitor();
		OWLReasonerConfiguration config 			= new SimpleConfiguration(progressMonitor);
		reasoner 									= reasonerFactory.createReasoner(ont, config);
		System.out.println("=================================== INIT ONTOLOGY HAS NOT EXCEPTION ============");
	}
	
	
	/**
	 * This function to test methods.
	 * @param args
	 * @throws OWLException
	 */
	public static void main(String[] args) throws OWLException {
		System.out.println("TEST READ ONTOLOGY");
		//testOWLAPI();
		System.out.println("END TEST: ");
	}
	
	
	/**
	 * This main method is used to test owl api.
	 * @throws OWLException
	 */
	public static void testOWLAPI() throws OWLException {
		
	}
	
	/**
	 * This method is used to get {@link Set} {@link OWLIndividual} has relation with individual.
	 * @param individual
	 * @return
	 * @throws OWLException
	 */
	public static Set<OWLNamedIndividual> getIRIOfIndividualByObjectProperties(String individual) throws OWLException {
		OWLNamedIndividual individ 		= getIRIOfIndividual(individual);
		Set<OWLNamedIndividual> values 	= new HashSet<OWLNamedIndividual>();
		for (String iriObjectProperty : OBJECT_PROPERTIES_LIST) {
			OWLObjectProperty oProperty 	= fac.getOWLObjectProperty(IRI.create(iriObjectProperty));
			NodeSet<OWLNamedIndividual> valuesNodeSet 	= reasoner.getObjectPropertyValues(individ, oProperty);
			values.addAll(valuesNodeSet.getFlattened());
		}
		return values;
	}
	
	/**
	 * This method is used to get {@link Set} {@link OWLIndividual} has iriObjectProperties relation with individual.
	 * @param individual
	 * @param iriObjectProperties
	 * @return
	 * @throws OWLException
	 */
	public static Set<OWLNamedIndividual> getIRIOfIndividualByObjectProperties(String individual, String iriObjectProperties) throws OWLException {
		OWLNamedIndividual individ 		= getIRIOfIndividual(individual);
		OWLObjectProperty oProperty 	= fac.getOWLObjectProperty(IRI.create(iriObjectProperties));
		NodeSet<OWLNamedIndividual> valuesNodeSet 	= reasoner.getObjectPropertyValues(individ, oProperty);
		Set<OWLNamedIndividual> values 				= valuesNodeSet.getFlattened();
		return values;
	}
	
	
	/**
	 * This method will get all individuals in the same class by one individual.
	 * @param individual
	 * @return
	 * @throws OWLException
	 */
	public static Set<OWLNamedIndividual> getIndividualsInSameClassByOneIndividual(String individual) throws OWLException {
		OWLClass owlClass 								= getOWLClassByIndividua(individual);
		NodeSet<OWLNamedIndividual> individualsNodeSet 	= reasoner.getInstances(owlClass, true);
		Set<OWLNamedIndividual> owlNamedIndividuals 	= individualsNodeSet.getFlattened();
		return owlNamedIndividuals;
	}
	
	
	/**
	 * This method check individual is contained in my ontology.
	 * @param individual
	 * @return <code>true</code> if ontology has individual else return <code>false</code>
	 * @throws OWLOntologyCreationException
	 */
	public static boolean checkOntologyHasIndividua(String individual) throws OWLException {
		if (individual == null || individual == "") return false;
		
		//format value of individual
		individual = individual.trim().replace(" ", "_").toUpperCase();
		Set<OWLNamedIndividual> owlNamedIndividuals = ont.getIndividualsInSignature();
		
		for (OWLNamedIndividual owlNamedIndividual : owlNamedIndividuals) {
			if (owlNamedIndividual.getIRI().getFragment().trim().equalsIgnoreCase(individual)) return true;
		}
		
		return false;
	}
	
	/**
	 * This method get {@link OWLNamedIndividual} by individual value.
	 * @param individual
	 * @return OWLNamedIndividual if ontology has individual value else return null.
	 * @throws OWLOntologyCreationException
	 */
	public static OWLNamedIndividual getIRIOfIndividual(String individual) throws OWLException {
		if (individual == null || individual == "") return null;
		
		individual 									= individual.trim().replace(" ", "_").toUpperCase();		
		Set<OWLNamedIndividual> owlNamedIndividuals = ont.getIndividualsInSignature();
		
		for (OWLNamedIndividual owlNamedIndividual : owlNamedIndividuals) {
			if (owlNamedIndividual.getIRI().getFragment().trim().equalsIgnoreCase(individual)) return owlNamedIndividual;
		}
		
		return null;
	}
	
	/**
	 * This method will get {@link OWLClass} by individual.
	 * @param individual
	 * @return
	 * @throws OWLException
	 */
	public static OWLClass getOWLClassByIndividua(String individual) throws OWLException {
		if (individual == null || individual == "") return null;
		
		//format value of individual
		individual = individual.trim().replace(" ", "_").toUpperCase();
		
		Set<OWLClass> owlClasses 					= ont.getClassesInSignature();
		
		for (OWLClass owlClass : owlClasses) {
			NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(owlClass, true);
			Set<OWLNamedIndividual> owlNamedIndividuals = individualsNodeSet.getFlattened();
			for (OWLNamedIndividual owlNamedIndividual : owlNamedIndividuals) {
				if (owlNamedIndividual.getIRI().getFragment().trim().equalsIgnoreCase(individual)) return owlClass;
			}
		}
		return null;
	}
	
}
