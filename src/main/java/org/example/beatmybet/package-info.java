@AnyMetaDef(name = "EntityDescriptionMetaDef", metaType = "string", idType = "long",
        metaValues = {
                @MetaValue(value = "USER", targetEntity = User.class),
                @MetaValue(value = "BID", targetEntity = Bid.class),
                @MetaValue(value = "FINANCE", targetEntity = FinanceType.class),
        })
package org.example.beatmybet;

import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.FinanceType;
import org.example.beatmybet.entity.User;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

//for polimorphic relations in db :|