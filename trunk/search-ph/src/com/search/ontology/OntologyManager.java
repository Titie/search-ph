package com.search.ontology;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
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
 * This class manager ontology: access to read individual and relate with other individual.
 * @author HoangAnh
 *
 */
public class OntologyManager {

	
	private static final String PIZZA_IRI = "http://130.88.198.11/co-ode-files/ontologies/pizza.owl";
	private static final String COMPUTER_ONTOLOGY_FILE = "D:\\Java\\workspace\\search-ph\\ontology\\computer-ontology.owl";
	public static void main(String[] args) throws OWLException {
		System.out.println("TEST READ ONTOLOGY");
		
		OWLOntology ontology = loadOntology(COMPUTER_ONTOLOGY_FILE);
		
//		Set<OWLClass> classes =  ontology.getClassesInSignature();
//		Iterator<OWLClass> iterator = classes.iterator();
//		while (iterator.hasNext()) {
//			OWLClass owlClass = iterator.next();
//			System.out.println(owlClass.getClassesInSignature());
//		}
//		System.out.println(classes.size());
		System.out.println("END TEST");
	}
	
	
	
	public static OWLOntology loadOntology(String pathfile) throws OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		File file = new File(pathfile);
		OWLOntology ont = manager.loadOntologyFromOntologyDocument(file);
		
		System.out.println("Loaded: " + ont.getOntologyID());
		
		OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
		ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
		OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
		OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);
		reasoner.precomputeInferences();
		boolean consistent = reasoner.isConsistent();
		System.out.println("Consistent: " + consistent);
		Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
		Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
		if (!unsatisfiable.isEmpty()) {
			System.out.println("The following classes are unsatisfiable: ");
			for (OWLClass cls : unsatisfiable) {
				System.out.println(" " + cls);
			}
		} else {
			System.out.println("There are no unsatisfiable classes");
		}
		
		
		OWLDataFactory fac = manager.getOWLDataFactory();
		OWLClass BMC = fac.getOWLClass(IRI.create("http://www.semanticweb.org/anhh1/ontologies/2014/1/untitled-ontology-13#Bo_Mạch_Chủ"));
		OWLClass HDH = fac.getOWLClass(IRI.create("http://www.semanticweb.org/anhh1/ontologies/2014/1/untitled-ontology-13#Hệ_Điều_Hành"));
		
		NodeSet<OWLClass> subClsesBMC = reasoner.getSubClasses(BMC, true);
		
		Set<OWLClass> clses = subClsesBMC.getFlattened();
		System.out.println("Subclasses of BMC: ");
		for (OWLClass cls : clses) {
			System.out.println(" " + cls);
		}
		System.out.println("\n");
		
		
		NodeSet<OWLClass> subClsesHDH = reasoner.getSubClasses(HDH, true);
		
		Set<OWLClass> clsesHDH = subClsesHDH.getFlattened();
		System.out.println("Subclasses of HDH: ");
		for (OWLClass cls : clsesHDH) {
			System.out.println(" " + cls);
			
			NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(cls, true);
			Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
			System.out.println("Instances of zz: ");
			for (OWLNamedIndividual ind : individuals) {
				System.out.println(" " + ind);
			}
			System.out.println("\n");
		
		}
		System.out.println("\n");
		
		return ont;
	}
	
	/**
	 * The examples here show how to load ontologies.
	 * 
	 * @throws OWLOntologyCreationException
	 */
	public static void shouldLoad() throws OWLOntologyCreationException {
		// Get hold of an ontology manager
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		// Let's load an ontology from the web
		IRI iri = IRI.create(PIZZA_IRI);
		OWLOntology pizzaOntology = manager.loadOntologyFromOntologyDocument(iri);
		System.out.println("Loaded ontology: " + pizzaOntology);
		// Remove the ontology so that we can load a local copy.
		manager.removeOntology(pizzaOntology);
		// We can also load ontologies from files. Download the pizza ontology
		// from http://owl.cs.manchester.ac.uk/co-ode-files/ontologies/pizza.owl
		// and put it
		// somewhere on your hard drive Create a file object that points to the
		// local copy
		File file = new File("/ontology/pizza.owl");
		System.out.println("FILE LOAD:" + file.getAbsolutePath());
		// Now load the local copy
		OWLOntology localPizza = manager.loadOntologyFromOntologyDocument(file);
		System.out.println("Loaded ontology: " + localPizza);
		// We can always obtain the location where an ontology was loaded from
		IRI documentIRI = manager.getOntologyDocumentIRI(localPizza);
		System.out.println(" from: " + documentIRI);
		// Remove the ontology again so we can reload it later
		manager.removeOntology(pizzaOntology);
		// In cases where a local copy of one of more ontologies is used, an
		// ontology IRI mapper can be used to provide a redirection mechanism.
		// This means that ontologies can be loaded as if they were located on
		// the web. In this example, we simply redirect the loading from
		// http://owl.cs.manchester.ac.uk/co-ode-files/ontologies/pizza.owl to
		// our local copy
		// above.
		manager.addIRIMapper(new SimpleIRIMapper(iri, IRI.create(file)));
		// Load the ontology as if we were loading it from the web (from its
		// ontology IRI)
		IRI pizzaOntologyIRI = IRI.create("http://owl.cs.manchester.ac.uk/co-ode-files/ontologies/pizza.owl");
		OWLOntology redirectedPizza = manager.loadOntology(pizzaOntologyIRI);
		System.out.println("Loaded ontology: " + redirectedPizza);
		System.out.println(" from: "
				+ manager.getOntologyDocumentIRI(redirectedPizza));
		// Note that when imports are loaded an ontology manager will be
		// searched for mappings
	}
	
}
