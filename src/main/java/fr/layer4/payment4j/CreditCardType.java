package fr.layer4.payment4j;

import com.google.common.base.Optional;

// http://en.wikipedia.org/wiki/Credit_card_number#Major_Industry_Identifier_.28MII.29
public enum CreditCardType implements CreditCardValidator {

	VISA {
		private static final String PREFIX = "4";

		public Optional<Boolean> matches(String card) {
			return Optional.of((card.substring(0, 1).equals(PREFIX) && (card
					.length() == 13 || card.length() == 16)));
		}
	},
	VISA_ELECTRON {
		private static final String PREFIX = "4026,4175,4405,4508,4844,4913,4917,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of((PREFIX.indexOf(card.substring(0, 4) + ",") != -1 && (card
							.length() == 13 || card.length() == 16)));
		}
	},
	MASTERCARD {
		private static final String PREFIX = "51,52,53,54,55,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 2) + ",") != -1) && (card
							.length() == 16)));
		}
	},
	DISCOVER {
		// TODO
		private static final String PREFIX = "6011,65,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 4) + ",") != -1) && (card
							.length() == 16)));
		}
	},
	AMERICAN_EXPRESS {
		private static final String PREFIX = "34,37,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 2) + ",") != -1) && (card
							.length() == 15)));
		}
	},
	DINERS_CLUB {
		private static final String PREFIX = "300,301,302,303,304,305,309,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 3) + ",") != -1) && (card
							.length() == 14)));
		}
	},
	JCB {
		public Optional<Boolean> matches(String card) {
			int prefix = Integer.valueOf(card.substring(0, 2));
			return Optional.of(((prefix >= 3528 && prefix <= 3589) && (card
					.length() == 16)));
		}
	},
	SWITCH {
		// TODO
		private static final String PREFIX = "4903,4905,4911,4936,564182,633110,6333,6759,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 4) + ",") != -1) && (card
							.length() == 16 || card.length() == 18 || card
							.length() == 19)));
		}
	},
	SOLO {
		private static final String PREFIX = "6334,6767,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 4) + ",") != -1) && (card
							.length() == 16 || card.length() == 18 || card
							.length() == 19)));
		}
	},
	DANKORT {
		private static final String PREFIX = "5019";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 4) + ",") != -1) && (card
							.length() == 16)));
		}
	},
	MAESTRO {
		private static final String PREFIX = "5018,5020,5038,5612,5893,6304,6759,6761,6762,6763,0604,6390,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 4) + ",") != -1) && (card
							.length() == 16)));
		}
	},
	FORBRUGSFORENINGEN {
		// TODO
		public Optional<Boolean> matches(String card) {
			return Optional.absent();
		}
	},
	LASER {
		private static final String PREFIX = "6304,6706,6771,6709,";

		public Optional<Boolean> matches(String card) {
			return Optional
					.of(((PREFIX.indexOf(card.substring(0, 4) + ",") != -1) && (card
							.length() == 16 || card.length() == 19)));
		}
	};
}
