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

package org.gephi.appearance.api;

import java.awt.Color;
import java.util.Collection;
import org.gephi.graph.api.Column;
import org.gephi.graph.api.Element;
import org.gephi.graph.api.Graph;

/**
 * Partition configuration for categorical attributes.
 * <p>
 * This interface has access to the underlying index so it can return the
 * complete collection of different values, as well as the number of elements
 * with this value.
 * <p>
 * Note that <code>null</code> can be a valid value.
 * <p>
 * Colors can be associated with values.
 */
public interface Partition {

    public static Color DEFAULT_COLOR = Color.LIGHT_GRAY;

    /**
     * Returns the collection of values this partition represents. Each value
     * has at least one element.
     *
     * @return values
     */
    Collection getValues(Graph graph);

    /**
     * Returns the same collection as {@link #getValues(Graph graph) } but sorted
     * descendant in counts.
     *
     * @return sorted values
     */
    Collection getSortedValues(Graph graph);

    /**
     * Returns the number of elements that have a value in this partition.
     *
     * @return element count
     */
    int getElementCount(Graph graph);

    /**
     * Returns the number of elements for the given value.
     *
     * @param value value
     * @return value count
     */
    int count(Object value, Graph graph);

    /**
     * Returns the element's value for this partition.
     *
     * @param element element to get the value for
     * @param graph   graph this element belongs to
     * @return the value for this partition
     */
    Object getValue(Element element, Graph graph);

    /**
     * Returns the color for the given value.
     *
     * @param value value to get the color for
     * @return color or null if not defined
     */
    Color getColor(Object value);

    /**
     * Sets the color for the given value.
     *
     * @param value value to set the color for
     * @param color color
     */
    void setColor(Object value, Color color);

    /**
     * Returns the percentage of elements with the given value.
     *
     * @param value value
     * @return percentage, between zero and 100
     */
    float percentage(Object value, Graph graph);

    /**
     * Returns the number of values this partition represents.
     *
     * @return value count
     */
    int size(Graph graph);

    /**
     * Returns the column associated with this partition.
     *
     * @return column or null if partition not based on a column
     */
    Column getColumn();
}
