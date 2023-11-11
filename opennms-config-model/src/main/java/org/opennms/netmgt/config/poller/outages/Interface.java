/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2011-2017 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2017 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.netmgt.config.poller.outages;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.opennms.core.utils.InetAddressUtils;
import org.opennms.core.xml.ValidateUsing;

/**
 * Interface to which the outage applies.
 */

@XmlRootElement(name="interface", namespace="http://xmlns.opennms.org/xsd/config/poller/outages")
@XmlAccessorType(XmlAccessType.FIELD)
@ValidateUsing("poll-outages.xsd")
public class Interface implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * IP address
     */
    @XmlAttribute(name="address")
    private String m_address;

    public Interface() {
    }

    public String getAddress() {
        return m_address;
    }

    public void setAddress(final String address) {
        if (!isValidAddress(address)) {
            throw new IllegalArgumentException("'address' is a required field and must be either a valid IP address, or 'match-any'!");
        }
        m_address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_address);
    }

    @Override
    public boolean equals(final Object obj) {
        if ( this == obj ) {
            return true;
        }

        if (obj instanceof Interface) {
            final Interface that = (Interface)obj;
            return Objects.equals(this.m_address, that.m_address);
        }
        return false;
    }

    private boolean isValidAddress(final String addr) {
        if (addr == null) return false;
        if ("match-any".equals(addr)) {
            return true;
        }
        try {
            final InetAddress inetAddr = InetAddressUtils.addr(addr);
            if (inetAddr == null) {
                return false;
            }
        } catch (final Exception e) {
            return false;
        }

        return true;
    }
}