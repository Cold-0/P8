package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.EstateType

object DataProvider {

    const val loremIpsum: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor is, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit."


    val estateList: List<Estate> = listOf(
        Estate(
            address = "Manhattan",
            type = EstateType.Flat,
            description = loremIpsum,
            price = 17870000
        ),
        Estate(
            address = "Montauk",
            type = EstateType.House,
            description = loremIpsum,
            price = 21130000
        ),
        Estate(
            address = "Brooklyn",
            type = EstateType.Duplex,
            description = loremIpsum,
            price = 13990000
        ),
        Estate(
            address = "Southampton",
            type = EstateType.House,
            description = loremIpsum,
            price = 41480000
        ),
        Estate(
            address = "Upper East Side",
            type = EstateType.Penthouse,
            description = loremIpsum,
            price = 29872000
        ),
        Estate(
            address = "Manhattan",
            type = EstateType.Flat,
            description = loremIpsum,
            price = 17870000
        ),
        Estate(
            address = "Montauk",
            type = EstateType.House,
            description = loremIpsum,
            price = 21130000
        ),
        Estate(
            address = "Brooklyn",
            type = EstateType.Duplex,
            description = loremIpsum,
            price = 13990000
        ),
        Estate(
            address = "Southampton",
            type = EstateType.House,
            description = loremIpsum,
            price = 41480000
        ),
        Estate(
            address = "Upper East Side",
            type = EstateType.Penthouse,
            description = loremIpsum,
            price = 29872000
        )
    )
}