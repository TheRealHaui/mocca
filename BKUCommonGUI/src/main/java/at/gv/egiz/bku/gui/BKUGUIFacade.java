/*
 * Copyright 2008 Federal Chancellery Austria and
 * Graz University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.gv.egiz.bku.gui;

import at.gv.egiz.stal.HashDataInput;
import at.gv.egiz.smcc.PINSpec;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

public interface BKUGUIFacade {

  public static final String ERR_UNKNOWN_WITH_PARAM = "error.unknown.param";
  public static final String ERR_UNKNOWN = "error.unknown";
  public static final String ERR_SERVICE_UNREACHABLE = "error.ws.unreachable";
  public static final String ERR_NO_PCSC = "error.pcsc";
  public static final String ERR_NO_CARDTERMINAL = "error.cardterminal";
  public static final String ERR_NO_HASHDATA = "error.no.hashdata";
  public static final String ERR_DISPLAY_HASHDATA = "error.display.hashdata";
  public static final String ERR_WRITE_HASHDATA = "error.write.hashdata";
  public static final String ERR_INVALID_HASH = "error.invalid.hash";
  public static final String ERR_CARD_LOCKED = "error.card.locked";
  public static final String ERR_CARD_NOTACTIVATED = "error.card.notactivated";
  public static final String ERR_PIN_TIMEOUT = "error.pin.timeout";
  public static final String ERR_VIEWER = "error.viewer";
  public static final String ERR_EXTERNAL_LINK = "error.external.link";
  public static final String ERR_CONFIG = "error.config";
  
  public static final String MESSAGES_BUNDLE = "at/gv/egiz/bku/gui/Messages";
  public static final String DEFAULT_BACKGROUND = "/images/ChipperlingCutoff.png";
  public static final String DEFAULT_ICON = "/images/ChipperlingLogo.png";
  public static final String HELP_IMG = "/images/help.png";
  public static final String HASHDATA_FONT = "Monospaced";
  public static final Color ERROR_COLOR = Color.RED;
  public static final Color HYPERLINK_COLOR = Color.BLUE;
  public static final String TITLE_WELCOME = "title.welcome";
  public static final String TITLE_INSERTCARD = "title.insertcard";
  public static final String TITLE_CARD_NOT_SUPPORTED = "title.cardnotsupported";
  public static final String TITLE_CARDPIN = "title.cardpin";
  public static final String TITLE_SIGN = "title.sign";
  public static final String TITLE_ERROR = "title.error";
  public static final String TITLE_ENTRY_TIMEOUT = "title.entry.timeout";
  public static final String TITLE_RETRY = "title.retry";
  public static final String TITLE_WAIT = "title.wait";
  public static final String TITLE_HASHDATA = "title.hashdata";
  public static final String WINDOWTITLE_SAVE = "windowtitle.save";
  public static final String WINDOWTITLE_ERROR = "windowtitle.error";
  public static final String WINDOWTITLE_SAVEDIR = "windowtitle.savedir";
  public static final String WINDOWTITLE_OVERWRITE = "windowtitle.overwrite";
  public static final String WINDOWTITLE_VIEWER = "windowtitle.viewer";
  public static final String WINDOWTITLE_HELP = "windowtitle.help";

  // removed message.* prefix to reuse keys as help keys
  public static final String MESSAGE_WELCOME = "welcome";
  public static final String MESSAGE_WAIT = "wait";
  public static final String MESSAGE_INSERTCARD = "insertcard";
  public static final String MESSAGE_CARD_NOT_SUPPORTED = "cardnotsupported";
  public static final String MESSAGE_ENTERPIN = "enterpin";
  public static final String MESSAGE_ENTERPIN_PINPAD = "enterpin.pinpad";
  public static final String MESSAGE_HASHDATALINK = "hashdatalink";
  public static final String MESSAGE_HASHDATALINK_TINY = "hashdatalink.tiny";
  public static final String MESSAGE_HASHDATALIST = "hashdatalist";
  public static final String MESSAGE_RETRIES = "retries";
  public static final String MESSAGE_LAST_RETRY = "retries.last";
  public static final String MESSAGE_RETRIES_PINPAD = "retries.pinpad";
  public static final String MESSAGE_LAST_RETRY_PINPAD = "retries.pinpad.last";
  public static final String MESSAGE_OVERWRITE = "overwrite";
  public static final String MESSAGE_HELP = "help";

  public static final String WARNING_XHTML = "warning.xhtml";
  public static final String LABEL_PIN = "label.pin";
  public static final String LABEL_PINSIZE = "label.pinsize";
  public static final String HELP_WELCOME = "help.welcome";
  public static final String HELP_WAIT = "help.wait";
  public static final String HELP_CARDNOTSUPPORTED = "help.cardnotsupported";
  public static final String HELP_INSERTCARD = "help.insertcard";
  public static final String HELP_CARDPIN = "help.cardpin";
  public static final String HELP_SIGNPIN = "help.signpin";
  public static final String HELP_RETRY = "help.retry";
  public static final String HELP_HASHDATA = "help.hashdata";
  public static final String HELP_HASHDATALIST = "help.hashdatalist";
  public static final String HELP_HASHDATAVIEWER = "help.hashdataviewer";
  public static final String BUTTON_OK = "button.ok";
  public static final String BUTTON_CANCEL = "button.cancel";
  public static final String BUTTON_BACK = "button.back";
  public static final String BUTTON_SIGN = "button.sign";
  public static final String BUTTON_SAVE = "button.save";
  public static final String BUTTON_CLOSE = "button.close";
  public static final String SAVE_HASHDATAINPUT_PREFIX = "save.hashdatainput.prefix";
  public static final String ALT_HELP = "alt.help";

  public enum Style { tiny, simple, advanced };
    
  /**
   * BKUWorker needs to init signature card with locale
   * @return
   */
  public Locale getLocale();

  public void showCardPINDialog(PINSpec pinSpec, int numRetries,
          ActionListener okListener, String okCommand,
          ActionListener cancelListener, String cancelCommand);

  public void showSignaturePINDialog(PINSpec pinSpec, int numRetries,
          ActionListener signListener, String signCommand,
          ActionListener cancelListener, String cancelCommand,
          ActionListener viewerListener, String viewerCommand);

  public void showPinpadSignaturePINDialog(PINSpec pinSpec, int numRetries, 
          ActionListener viewerListener, String viewerCommand);

  public char[] getPin();

  /**
   *
   * @param dataToBeSigned
   * @param backListener if list of references hides pin dialog, backListener
   * receives an action when user hits 'back' button (i.e. whenever the pin-dialog
   * needs to be re-paint)
   * @param backCommand
   */
  public void showSecureViewer(List<HashDataInput> dataToBeSigned,
          ActionListener backListener, String backCommand);

  public void showErrorDialog(String errorMsgKey, Object[] errorMsgParams,
          ActionListener okListener, String okCommand);

  public void showErrorDialog(String errorMsgKey, Object[] errorMsgParams);

  public void showMessageDialog(String titleKey, 
          String msgKey, Object[] msgParams,
          String buttonKey,
          ActionListener okListener, String okCommand);

  public void showMessageDialog(String titleKey,
          String msgKey, Object[] msgParams);

  public void showMessageDialog(String titleKey,
          String msgKey);
}
