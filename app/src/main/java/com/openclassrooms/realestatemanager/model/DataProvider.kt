package com.openclassrooms.realestatemanager.model

object DataProvider {

    const val loremIpsum: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor is, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit."


    val estateList: List<Estate> = listOf(
        Estate(
            district = "Manhattan",
            type = EstateType.Flat,
            description = loremIpsum,
            price = 17870000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/81/400"),
                Pair("Kitchen", "https://picsum.photos/id/5/400"),
                Pair("Bedroom", "https://picsum.photos/id/6/400")
            )
        ),
        Estate(
            district = "Montauk",
            type = EstateType.House,
            description = loremIpsum,
            price = 21130000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/82/400"),
                Pair("Kitchen", "https://picsum.photos/id/7/400"),
                Pair("Bedroom", "https://picsum.photos/id/8/400")
            )

        ),
        Estate(
            district = "Brooklyn",
            type = EstateType.Duplex,
            description = loremIpsum,
            price = 13990000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/83/400"),
                Pair("Kitchen", "https://picsum.photos/id/9/400"),
                Pair("Bedroom", "https://picsum.photos/id/10/400")
            )
        ),
        Estate(
            district = "Southampton",
            type = EstateType.House,
            description = loremIpsum,
            price = 41480000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/84/400"),
                Pair("Kitchen", "https://picsum.photos/id/11/400"),
                Pair("Bedroom", "https://picsum.photos/id/12/400")
            )
        ),
        Estate(
            district = "Upper East Side",
            type = EstateType.Penthouse,
            description = loremIpsum,
            price = 29872000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/85/400"),
                Pair("Kitchen", "https://picsum.photos/id/13/400"),
                Pair("Bedroom", "https://picsum.photos/id/14/400")
            )
        ),
        Estate(
            district = "Manhattan",
            type = EstateType.Flat,
            description = loremIpsum,
            price = 17870000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/87/400"),
                Pair("Kitchen", "https://picsum.photos/id/88/400"),
                Pair("Bedroom", "https://picsum.photos/id/100/400")
            )
        ),
        Estate(
            district = "Montauk",
            type = EstateType.House,
            description = loremIpsum,
            price = 21130000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/89/400"),
                Pair("Kitchen", "https://picsum.photos/id/15/400"),
                Pair("Bedroom", "https://picsum.photos/id/100/400")
            )
        ),
        Estate(
            district = "Brooklyn",
            type = EstateType.Duplex,
            description = loremIpsum,
            price = 13990000,
            pictures = listOf(
                Pair("Facade", "https://picsum.photos/id/33/400"),
                Pair("Kitchen", "https://picsum.photos/id/16/400"),
                Pair("Bedroom", "https://picsum.photos/id/17/400")
            )
        ),
        Estate(
            district = "Southampton",
            type = EstateType.House,
            description = loremIpsum,
            price = 41480000,
            pictures = listOf(
                Pair("Facade 1", "https://picsum.photos/id/450/400"),
                Pair("Facade 2", "https://picsum.photos/id/451/400"),
                Pair("Kitchen", "https://picsum.photos/id/452/400"),
                Pair("Bedroom 1", "https://picsum.photos/id/453/400"),
                Pair("Bedroom 2", "https://picsum.photos/id/454/400"),
            )
        ),
        Estate(
            district = "Upper East Side",
            type = EstateType.Penthouse,
            description = loremIpsum,
            price = 29872000,
            pictures = listOf(
                Pair("Facade 1", "https://picsum.photos/id/350/400"),
            )
        )
    )
}