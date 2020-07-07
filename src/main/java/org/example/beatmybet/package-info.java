@AnyMetaDef(name = "EntityDescriptionMetaDef", metaType = "string", idType = "int",
        metaValues = {
                @MetaValue(value = "user", targetEntity = User.class),
                @MetaValue(value = "bid", targetEntity = Bid.class),
                @MetaValue(value = "finance_type", targetEntity = FinanceType.class),

        })
package org.example.beatmybet;

import org.example.beatmybet.entity.Bid;
import org.example.beatmybet.entity.FinanceType;
import org.example.beatmybet.entity.User;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

//for polimorphic relations in db :|