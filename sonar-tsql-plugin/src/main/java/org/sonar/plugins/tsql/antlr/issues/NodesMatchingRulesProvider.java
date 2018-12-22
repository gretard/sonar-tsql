package org.sonar.plugins.tsql.antlr.issues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonar.plugins.tsql.antlr.CandidateNode;
import org.sonar.plugins.tsql.antlr.IParsedNode;
import org.sonar.plugins.tsql.antlr.nodes.INodesProvider;
import org.sonar.plugins.tsql.antlr.nodes.matchers.RulesMatcher;
import org.sonar.plugins.tsql.checks.custom.RuleImplementation;

public class NodesMatchingRulesProvider {

	private final INodesProvider<IParsedNode> nodeUsesProvider;
	private final RulesMatcher matcher = new RulesMatcher();

	public NodesMatchingRulesProvider(final INodesProvider<IParsedNode> nodeUsesProvider) {
		this.nodeUsesProvider = nodeUsesProvider;
	}

	public Map<RuleImplementation, List<IParsedNode>> check(CandidateNode candidate) {
		if (candidate == null || candidate.getRuleImplementation() == null) {
			throw new IllegalArgumentException("Candidate node or rule or rule implementation can't be null");
		}
		IParsedNode node = candidate.getNode();
		RuleImplementation rule = candidate.getRuleImplementation();
		Map<RuleImplementation, List<IParsedNode>> map = new HashMap<RuleImplementation, List<IParsedNode>>();
		initMap(map, rule);
		visit(node, null, rule, map);
		return map;
	}

	void visit(final IParsedNode node, final IParsedNode parent, final RuleImplementation rule,
			final Map<RuleImplementation, List<IParsedNode>> items) {
		if (this.matcher.match(rule, parent, node)) {
			items.putIfAbsent(rule, new ArrayList<>());
			if (!items.get(rule).contains(node)) {
				items.get(rule).add(node);
			}
			if (!rule.getSiblingsRules().getRuleImplementation().isEmpty()) {
				for (final IParsedNode nnode : node.getSiblings()) {
					for (final RuleImplementation vRule : rule.getSiblingsRules().getRuleImplementation()) {
						visit(nnode, node, vRule, items);
					}
				}
			}

			if (!rule.getChildrenRules().getRuleImplementation().isEmpty()) {
				for (final IParsedNode nnode : node.getChildren()) {
					for (final RuleImplementation vRule : rule.getChildrenRules().getRuleImplementation()) {
						visit(nnode, node, vRule, items);
					}
				}
			}

			if (!rule.getParentRules().getRuleImplementation().isEmpty()) {
				for (final IParsedNode nnode : node.getParents()) {
					for (final RuleImplementation vRule : rule.getParentRules().getRuleImplementation()) {
						visit(nnode, node, vRule, items);
					}
				}
			}

			if (!rule.getUsesRules().getRuleImplementation().isEmpty()) {
				for (final IParsedNode nnode : nodeUsesProvider.getNodes(node)) {
					for (final RuleImplementation vRule : rule.getUsesRules().getRuleImplementation()) {
						visit(nnode, node, vRule, items);
					}
				}
			}

		}
	}

	private static void initMap(final Map<RuleImplementation, List<IParsedNode>> map, final RuleImplementation rule) {
		map.putIfAbsent(rule, new ArrayList<>());

		for (final RuleImplementation i : rule.getChildrenRules().getRuleImplementation()) {
			initMap(map, i);
		}
		for (final RuleImplementation i : rule.getParentRules().getRuleImplementation()) {
			initMap(map, i);
		}
		for (final RuleImplementation i : rule.getUsesRules().getRuleImplementation()) {
			initMap(map, i);
		}
		for (final RuleImplementation i : rule.getSiblingsRules().getRuleImplementation()) {
			initMap(map, i);
		}
	}
}
