package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.model.Estate
import com.openclassrooms.realestatemanager.model.EstateType

object DataProvider {

    const val loremIpsum: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor is, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit."


    val estateList: List<Estate> = listOf(
        Estate(
            "Manhattan",
            EstateType.Flat,
            loremIpsum,
            17870000
        ),
        Estate(
            "Montauk",
            EstateType.House,
            loremIpsum,
            21130000
        ),
        Estate(
            "Brooklyn",
            EstateType.Duplex,
            loremIpsum,
            13990000
        ),
        Estate(
            "Southampton",
            EstateType.House,
            loremIpsum,
            41480000
        ),
        Estate(
            "Upper East Side",
            EstateType.Penthouse,
            loremIpsum,
            29872000
        ),
        Estate(
            "Manhattan",
            EstateType.Flat,
            loremIpsum,
            17870000
        ),
        Estate(
            "Montauk",
            EstateType.House,
            loremIpsum,
            21130000
        ),
        Estate(
            "Brooklyn",
            EstateType.Duplex,
            loremIpsum,
            13990000
        ),
        Estate(
            "Southampton",
            EstateType.House,
            loremIpsum,
            41480000
        ),
        Estate(
            "Upper East Side",
            EstateType.Penthouse,
            loremIpsum,
            29872000
        ),
    )
}