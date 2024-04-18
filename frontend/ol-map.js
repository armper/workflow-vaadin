import { LitElement, html, css } from 'lit';
import 'ol/ol.css';
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import Circle from 'ol/geom/Circle';
import { Style, Fill, Stroke, Icon } from 'ol/style';
import { fromLonLat } from 'ol/proj';
import Draw from 'ol/interaction/Draw'; // Import Draw interaction

class OlMap extends LitElement {
  static get properties() {
    return {
      radiusInMiles: { type: Number },
      mode: { type: String } // New property for interaction mode
    };
  }

  static get styles() {
    return css`
      :host {
        display: block;
        height: 75vh;
      }
      #map {
        height: 100%;
        width: 100%;
      }
    `;
  }

  constructor() {
    super();
    this.radiusInMiles = 20; // Default radius in miles
    this.map = null;
    this.mode = 'point'; // Default mode
    this.vectorSource = new VectorSource();

    this.vectorLayer = new VectorLayer({
      source: this.vectorSource,
      style: (feature) => {
        if (feature.getGeometry() instanceof Point) {
          return new Style({
            image: new Icon({
              src: 'https://openlayers.org/en/latest/examples/data/icon.png',
              anchor: [0.5, 46],
              anchorXUnits: 'fraction',
              anchorYUnits: 'pixels'
            })
          });
        } else if (feature.getGeometry() instanceof Circle) {
          return new Style({
            stroke: new Stroke({
              color: 'rgba(255, 100, 50, 0.9)',
              width: 2
            }),
            fill: new Fill({
              color: 'rgba(255, 100, 50, 0.2)'
            })
          });
        } else { // Style for the polygon
          return new Style({
            stroke: new Stroke({
              color: 'blue',
              width: 3
            }),
            fill: new Fill({
              color: 'rgba(0, 0, 255, 0.1)'
            })
          });
        }
      }
    });
  }

  firstUpdated() {
    this.map = new Map({
      target: this.renderRoot.querySelector('#map'),
      layers: [new TileLayer({ source: new OSM() }), this.vectorLayer],
      view: new View({
        center: fromLonLat([0, 0]),
        zoom: 2
      })
    });

    this.drawInteraction = new Draw({
      source: this.vectorSource,
      type: 'Polygon'
    });

    this.map.on('singleclick', event => this.handleMapClick(event));
  }

  handleMapClick(event) {
    if (this.mode === 'point') {
      const coordinate = this.map.getCoordinateFromPixel(event.pixel);
      const pointFeature = new Feature(new Point(coordinate));
      const radiusInMeters = this.radiusInMiles * 1609.34;
      const circleFeature = new Feature(new Circle(coordinate, radiusInMeters));

      this.vectorSource.clear();
      this.vectorSource.addFeatures([pointFeature, circleFeature]);

      // Emit point click event with coordinates
      this.dispatchEvent(new CustomEvent('point-click', {
        detail: { coordinate },
        bubbles: true,
        composed: true
      }));
    }
  }

  toggleMode(newMode) {
    if (this.mode !== newMode) {
      this.vectorSource.clear();

      if (newMode === 'polygon') {
        this.map.addInteraction(this.drawInteraction);
        this.drawInteraction.on('drawend', event => {
          this.map.removeInteraction(this.drawInteraction);

          // Emit polygon complete event with coordinates
          const coords = event.feature.getGeometry().getCoordinates();
          this.dispatchEvent(new CustomEvent('polygon-complete', {
            detail: { coords },
            bubbles: true,
            composed: true
          }));

        });
      } else {
        this.map.removeInteraction(this.drawInteraction);
      }
      this.mode = newMode;
    }
  }


  setRadius(radiusInMiles) {
    this.radiusInMiles = radiusInMiles;
  }

  render() {
    return html`<div id="map"></div>`;
  }
}

customElements.define('ol-map', OlMap);
