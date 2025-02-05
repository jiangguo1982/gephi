/*
 Copyright 2008-2013 Gephi
 Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
 Website : http://www.gephi.org

 This file is part of Gephi.

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2013 Gephi Consortium. All rights reserved.

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

 Portions Copyrighted 2013 Gephi Consortium.
 */

package org.gephi.appearance;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gephi.appearance.api.Partition;
import org.gephi.graph.api.Graph;

/**
 * @author mbastian
 */
public abstract class PartitionImpl implements Partition {

    protected final Map<Object, Color> colorMap;

    protected PartitionImpl() {
        this.colorMap = new HashMap<>();
    }

    @Override
    public Color getColor(Object value) {
        return colorMap.getOrDefault(value, Partition.DEFAULT_COLOR);
    }

    @Override
    public void setColor(Object value, Color color) {
        if (color.equals(Partition.DEFAULT_COLOR)) {
            colorMap.remove(value);
        } else {
            colorMap.put(value, color);
        }
    }

    @Override
    public Collection getSortedValues(Graph graph) {
        List values = new ArrayList(getValues(graph));
        values.sort((o1, o2) -> {
            int c1 = count(o1, graph);
            int c2 = count(o2, graph);
            return Integer.compare(c2, c1);
        });
        return values;
    }

    public abstract boolean isValid(Graph graph);

    public abstract Class getValueType();

    public abstract int getVersion(Graph graph);
}
