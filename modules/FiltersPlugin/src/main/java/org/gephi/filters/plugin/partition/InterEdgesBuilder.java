/*
 Copyright 2008-2010 Gephi
 Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
 Website : http://www.gephi.org

 This file is part of Gephi.

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2011 Gephi Consortium. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s):

 Portions Copyrighted 2011 Gephi Consortium.
 */

package org.gephi.filters.plugin.partition;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.appearance.api.AppearanceController;
import org.gephi.appearance.api.AppearanceModel;
import org.gephi.appearance.api.Partition;
import org.gephi.filters.api.FilterLibrary;
import org.gephi.filters.plugin.partition.PartitionBuilder.PartitionFilter;
import org.gephi.filters.spi.Category;
import org.gephi.filters.spi.CategoryBuilder;
import org.gephi.filters.spi.EdgeFilter;
import org.gephi.filters.spi.Filter;
import org.gephi.filters.spi.FilterBuilder;
import org.gephi.graph.api.Column;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * @author mbastian
 */
@ServiceProvider(service = CategoryBuilder.class)
public class InterEdgesBuilder implements CategoryBuilder {

    public final static Category INTER_EDGES = new Category(
        NbBundle.getMessage(InterEdgesBuilder.class, "InterEdgesBuilder.name"),
        null,
        FilterLibrary.ATTRIBUTES);

    @Override
    public Category getCategory() {
        return INTER_EDGES;
    }

    @Override
    public FilterBuilder[] getBuilders(Workspace workspace) {
        List<FilterBuilder> builders = new ArrayList<>();
        GraphModel gm = Lookup.getDefault().lookup(GraphController.class).getGraphModel(workspace);
        AppearanceModel am = Lookup.getDefault().lookup(AppearanceController.class).getModel(workspace);

        //Force refresh
        am.getNodeFunctions();

        for (Column nodeCol : gm.getNodeTable()) {
            if (!nodeCol.isProperty()) {
                Partition partition = am.getNodePartition(nodeCol);
                if (partition != null) {
                    InterEdgesFilterBuilder builder = new InterEdgesFilterBuilder(partition);
                    builders.add(builder);
                }
            }
        }

        return builders.toArray(new FilterBuilder[0]);
    }

    private static class InterEdgesFilterBuilder implements FilterBuilder {

        private final Partition partition;

        public InterEdgesFilterBuilder(Partition partition) {
            this.partition = partition;
        }

        @Override
        public Category getCategory() {
            return INTER_EDGES;
        }

        @Override
        public String getName() {
            return partition.getColumn().getTitle();
        }

        @Override
        public Icon getIcon() {
            return null;
        }

        @Override
        public String getDescription() {
            return NbBundle.getMessage(InterEdgesBuilder.class, "InterEdgesBuilder.description");
        }

        @Override
        public InterEdgesFilter getFilter(Workspace workspace) {
            return new InterEdgesFilter(partition);
        }

        @Override
        public JPanel getPanel(Filter filter) {
            PartitionUI ui = Lookup.getDefault().lookup(PartitionUI.class);
            if (ui != null) {
                return ui.getPanel((PartitionFilter) filter);
            }
            return null;
        }

        @Override
        public void destroy(Filter filter) {
        }
    }

    public static class InterEdgesFilter extends PartitionFilter implements EdgeFilter {

        public InterEdgesFilter(Partition partition) {
            super(partition);
        }

        @Override
        public String getName() {
            return NbBundle.getMessage(IntraEdgesBuilder.class, "InterEdgesBuilder.name") + " (" + partition.getColumn().getTitle() +
                ")";
        }

        @Override
        public boolean init(Graph graph) {
            this.graph = graph;
            return partition != null && partition.getColumn() != null;
        }

        @Override
        public boolean evaluate(Graph graph, Edge edge) {
            Object srcValue = partition.getValue(edge.getSource(), graph);
            Object destValue = partition.getValue(edge.getTarget(), graph);
            srcValue = srcValue == null ? NULL : srcValue;
            destValue = destValue == null ? NULL : destValue;
            return parts.contains(srcValue) && parts.contains(destValue) && srcValue.equals(destValue);
        }

        @Override
        public void finish() {
        }
    }
}
