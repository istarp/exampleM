package nz.co.example.app.features.characters.models

internal data class UIOCharacterScreenState(val isSearching: Boolean, val searchText: String) {

    companion object {
        fun default(): UIOCharacterScreenState {
            return UIOCharacterScreenState(isSearching = false, searchText = "")
        }

        fun previewSearchWithoutText(): UIOCharacterScreenState {
            return UIOCharacterScreenState(isSearching = true, searchText = "")
        }

        fun previewSearchWithText(): UIOCharacterScreenState {
            return UIOCharacterScreenState(isSearching = true, searchText = "Text")
        }
    }

}