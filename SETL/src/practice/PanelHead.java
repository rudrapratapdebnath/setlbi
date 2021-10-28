package practice;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;

import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;

import helper.Methods;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;

public class PanelHead extends JPanel {

	/**
	 * Create the panel.
	 */
	
	JComboBox comboBoxDataset;
	JTextField textFieldSourceConcept;
	JTextField textFieldTargetConcept;
	JComboBox comboBoxRelation;
	JComboBox comboBoxMapped;
	JTextArea textAreaSparql;
	JTextField textFieldValue;
	JComboBox comboBoxValueType;
	JTextField textFieldOperation;
	private JTextField textFieldNamespace;
	
	public PanelHead() {
		setBackground(Color.WHITE);
		setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panelHead = new JPanel();
		panelHead.setBackground(Color.WHITE);
		panelHead.setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		textFieldSourceConcept = new JTextField();
		textFieldTargetConcept = new JTextField();
		
		JLabel lblDataset = new JLabel("Dataset:");
		lblDataset.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelHead.add(lblDataset, "cell 0 0,alignx trailing");
		
//		comboBoxDataset = new JComboBox(recordDefinition.getDatasetList().toArray());
		comboBoxDataset = new JComboBox();
		comboBoxDataset.setBackground(Color.WHITE);
		comboBoxDataset.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelHead.add(comboBoxDataset, "cell 1 0,growx");
		
		JPanel panelConceptMapping = new JPanel();
		panelConceptMapping.setBorder(new TitledBorder(null, "Concept Mapping", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		panelConceptMapping.setBackground(Color.WHITE);
		panelHead.add(panelConceptMapping, "cell 0 1 2 1,grow");
		panelConceptMapping.setLayout(new MigLayout("", "[][grow][]", "[][][][][]"));
		
		JLabel lblSourceConcept = new JLabel("Source Concept:");
		lblSourceConcept.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(lblSourceConcept, "cell 0 1,alignx trailing");
		
		// textFieldSourceConcept = new JTextField();
		textFieldSourceConcept.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(textFieldSourceConcept, "cell 1 1 2 1,growx");
		textFieldSourceConcept.setColumns(10);
		
		JLabel lblTargetConcept = new JLabel("Target Concept:");
		lblTargetConcept.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(lblTargetConcept, "cell 0 0,alignx trailing");
		
		// textFieldTargetConcept = new JTextField();
		textFieldTargetConcept.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(textFieldTargetConcept, "cell 1 0 2 1,growx");
		textFieldTargetConcept.setColumns(10);
		
		JLabel lblRelation = new JLabel("Relation:");
		lblRelation.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(lblRelation, "cell 0 2,alignx trailing");
		
		JLabel lblSourceABox = new JLabel("Source ABox Location:");
		lblSourceABox.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(lblSourceABox, "cell 0 3,alignx trailing");
		
		JTextField textFieldSourceABoxPath = new JTextField();
		textFieldSourceABoxPath.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(textFieldSourceABoxPath, "cell 1 3,growx");
		textFieldSourceABoxPath.setColumns(10);
		
		JButton btnSourceABox = new JButton("Open");
		btnSourceABox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Methods methods = new Methods();
				String sourceABoxLocationString = methods.chooseFile("Select Source ABox File");
				
				if (methods.checkString(sourceABoxLocationString)) {
					textFieldSourceABoxPath.setText(sourceABoxLocationString);
				}
			}
		});
		btnSourceABox.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(btnSourceABox, "cell 2 3");
		
		JLabel lblTargetABox = new JLabel("Target ABox Location:");
		lblTargetABox.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(lblTargetABox, "cell 0 4,alignx trailing");
		
		JTextField textFieldTargetABoxPath = new JTextField();
		textFieldTargetABoxPath.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(textFieldTargetABoxPath, "cell 1 4,growx");
		textFieldTargetABoxPath.setColumns(10);
		
		JButton btnTargetABox = new JButton("Open");
		btnTargetABox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Methods methods = new Methods();
				String sourceABoxLocationString = methods.chooseFile("Select Source ABox File");
				
				if (methods.checkString(sourceABoxLocationString)) {
					textFieldTargetABoxPath.setText(sourceABoxLocationString);
				}
			}
		});
		btnTargetABox.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(btnTargetABox, "cell 2 4");
		
		// comboBoxRelation = new JComboBox(recordDefinition.getRelations());
		comboBoxRelation = new JComboBox();
		comboBoxRelation.setBackground(Color.WHITE);
		comboBoxRelation.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelConceptMapping.add(comboBoxRelation, "cell 1 2 2 1,growx");
		
		JPanel panelInstanceMatching = new JPanel();
		panelInstanceMatching.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Instance Mapping", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelInstanceMatching.setBackground(Color.WHITE);
		panelHead.add(panelInstanceMatching, "cell 0 2 2 1,grow");
		panelInstanceMatching.setLayout(new MigLayout("", "[][grow]", "[][][]"));
		
		JLabel lblInstancesToBe = new JLabel("Instances to be mapped:");
		lblInstancesToBe.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelInstanceMatching.add(lblInstancesToBe, "cell 0 0,alignx trailing");
		
		JPanel panelMapHolder = new JPanel();
		JPanel panelQuery = new JPanel();
		
//		comboBoxMapped = new JComboBox(recordDefinition.getSourceMapperType().toArray());
		comboBoxMapped = new JComboBox();
		comboBoxMapped.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		comboBoxMapped.setBackground(Color.WHITE);
		comboBoxMapped.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelInstanceMatching.add(comboBoxMapped, "cell 1 0,growx");
		
		panelMapHolder.setBackground(Color.WHITE);
		panelInstanceMatching.add(panelMapHolder, "cell 0 1 2 1,grow");
		panelMapHolder.setLayout(new CardLayout(0, 0));
		
		panelQuery.setBackground(Color.WHITE);
		// panelMapHolder.add(panelQuery, PANEL_QUERY);
		panelQuery.setLayout(new MigLayout("", "[][grow]", "[]"));
		
		JLabel lblSourceQuery = new JLabel("Source Query:");
		lblSourceQuery.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelQuery.add(lblSourceQuery, "cell 0 0");
		
		JScrollPane scrollPaneSparql = new JScrollPane();
		scrollPaneSparql.setMinimumSize(new Dimension(panelQuery.getWidth(), 50));
		panelQuery.add(scrollPaneSparql, "cell 1 0,grow");
		
		textAreaSparql = new JTextArea();
		textAreaSparql.setWrapStyleWord(true);
		textAreaSparql.setRows(5);
		textAreaSparql.setLineWrap(true);
		textAreaSparql.setFont(new Font("Tahoma", Font.BOLD, 12));
		// panelQuery.add(textAreaSparql, "cell 1 0,grow");
		scrollPaneSparql.setViewportView(textAreaSparql);
		
		JPanel panelValueSelection = new JPanel();
		panelValueSelection.setBorder(new TitledBorder(null, "Value for Creating Target Instances' IRI", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		panelValueSelection.setBackground(Color.WHITE);
		panelInstanceMatching.add(panelValueSelection, "cell 0 2 2 1,grow");
		panelValueSelection.setLayout(new MigLayout("", "[][grow]", "[grow][][][]"));
		
		JPanel panelValueHolder = new JPanel();
		JPanel panelValue = new JPanel();
		JPanel panelOther = new JPanel();
		JPanel panelExpression = new JPanel();
		
		JPanel panelNamespace = new JPanel();
		panelNamespace.setBackground(Color.WHITE);
		panelValueSelection.add(panelNamespace, "cell 0 0 2 1,grow");
		panelNamespace.setLayout(new MigLayout("", "[][][][grow]", "[grow][]"));
		
		JLabel lblNamespace = new JLabel("Namespace: ");
		lblNamespace.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelNamespace.add(lblNamespace, "cell 0 0");
		
		JRadioButton rdbtnDefault = new JRadioButton("Default");
		rdbtnDefault.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelNamespace.add(rdbtnDefault, "cell 1 0");
		
		JRadioButton rdbtnCustom = new JRadioButton("Custom");
		rdbtnCustom.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelNamespace.add(rdbtnCustom, "cell 2 0");
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnDefault);
	    group.add(rdbtnCustom);
	    

		rdbtnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnCustom.setEnabled(false);
			}
		});
		

		rdbtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnDefault.setEnabled(false);
			}
		});
		
		textFieldNamespace = new JTextField();
		panelNamespace.add(textFieldNamespace, "cell 3 0,growx");
		textFieldNamespace.setColumns(10);
		
		JLabel lblValueType = new JLabel("Value Type:");
		lblValueType.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelValueSelection.add(lblValueType, "cell 0 1,alignx trailing");
		
		String[] keyTypes = {"Source Attribute", "Expression", "Incremental", "Automatic", "Same As Source IRI"};
		comboBoxValueType = new JComboBox(keyTypes);
		comboBoxValueType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		comboBoxValueType.setBackground(Color.WHITE);
		comboBoxValueType.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelValueSelection.add(comboBoxValueType, "cell 1 1,growx");
		
		panelValueHolder.setBackground(Color.WHITE);
		panelValueSelection.add(panelValueHolder, "cell 0 2 2 1,grow");
		panelValueHolder.setLayout(new CardLayout(0, 0));
		
		// panelValueHolder.add(panelExpression, PANEL_CONCEPT_EXPRESSION);
		
		panelOther.setBackground(Color.WHITE);
		// panelValueHolder.add(panelOther, PANEL_CONCEPT_OTHER);
		panelOther.setLayout(new MigLayout("", "[]", "[]"));
		
		panelValue.setBackground(Color.WHITE);
		panelValue.setLayout(new MigLayout("", "[][grow]", "[]"));
		
		JLabel lblValue = new JLabel("Value:");
		lblValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelValue.add(lblValue, "cell 0 0,alignx trailing");
		
		textFieldValue = new JTextField();
		textFieldValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelValue.add(textFieldValue, "cell 1 0,growx");
		textFieldValue.setColumns(10);
		
		JLabel lblOperation = new JLabel("Operation:");
		lblOperation.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelValueSelection.add(lblOperation, "cell 0 3,alignx trailing");
		
		textFieldOperation = new JTextField();
		textFieldOperation.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelValueSelection.add(textFieldOperation, "cell 1 3,growx");
		textFieldOperation.setColumns(10);
		
		JPanel panelCommon = new JPanel();
		panelCommon.setBorder(new TitledBorder(null, "Common Property", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		panelCommon.setBackground(Color.WHITE);
		panelHead.add(panelCommon, "cell 0 3 2 1,grow");
		panelCommon.setLayout(new MigLayout("", "[][grow]", "[][]"));
		
		JLabel lblSourceCommonProperty = new JLabel("Source Common Property:");
		lblSourceCommonProperty.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelCommon.add(lblSourceCommonProperty, "cell 0 0,alignx trailing");
		
		ArrayList<String> propertyList = new ArrayList<>();
		
		JComboBox comboBoxSourceProperty = new JComboBox(propertyList.toArray());
		comboBoxSourceProperty.setBackground(Color.WHITE);
		comboBoxSourceProperty.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelCommon.add(comboBoxSourceProperty, "cell 1 0,growx");
		
		JLabel lblTargetCommonProperty = new JLabel("Target Common Property:");
		lblTargetCommonProperty.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelCommon.add(lblTargetCommonProperty, "cell 0 2,alignx trailing");
		
		ArrayList<String> propertyList2 = new ArrayList<>();
		
		JComboBox comboBoxTargetProperty = new JComboBox(propertyList2.toArray());
		comboBoxTargetProperty.setBackground(Color.WHITE);
		comboBoxTargetProperty.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelCommon.add(comboBoxTargetProperty, "cell 1 2,growx");
		
		JCheckBox chckbxBothSame = new JCheckBox("Both Same");
		chckbxBothSame.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (chckbxBothSame.isSelected()) {
					comboBoxTargetProperty.setSelectedItem(comboBoxSourceProperty.getSelectedItem());
				}
			}
		});
		chckbxBothSame.setBackground(Color.WHITE);
		panelCommon.add(chckbxBothSame, "cell 1 1");
		
		JButton btnSaveConcept = new JButton("Save Concept");
		btnSaveConcept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnSaveConcept.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelHead.add(btnSaveConcept, "cell 0 4 2 1,center");

	}

}
